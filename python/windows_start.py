import json
import subprocess
import sys

#############################################################################
#                                                                           #
#                               config                                      #
#                                                                           #
#############################################################################

serverJson = sys.argv[1];
with open(serverJson) as f:
    data = json.load(f)
    adminUsername = data['adminUsername']
    adminPassword = data['adminPassword']
    adminURL = data['adminURL']
    setDomainEnv = data['setDomainEnv']
    execWlst = data['execWlst']
    print(adminUsername)
    print(adminPassword)
    print(adminURL)
    print(execWlst)

#############################################################################
#                                                                           #
#                               script                                      #
#                                                                           #
#############################################################################

dataJson = sys.argv[2];

with open(dataJson) as f:
    data = json.load(f)
    print(data)

for i in data:
    name = i['name']
    jndiName = i['jndiName']
    dsURL = i['dsURL']
    user = i['user']
    password = i['password']
    target = i['target']
    driverClass = i['driverClass']

    configFile = sys.argv[3];

    copyCmd = "xcopy " + configFile + " temp /Y"
    subprocess.call(copyCmd, shell=True)

    tmpFile = "temp\\" + configFile

    f = open(tmpFile, "r")
    strs = f.readline().replace('weblogicAdminUsername', adminUsername)
    strs = strs + f.readline().replace('weblogicAdminPassword', adminPassword)
    strs = strs + f.readline().replace('t3://localhost:7001', adminURL)
    strs = strs + f.readline().replace('weblogicDatasourceName', name)
    strs = strs + f.readline().replace('weblogicJndiName', jndiName)
    strs = strs + f.readline().replace('dataSourceUrl', dsURL)
    strs = strs + f.readline().replace('dataSourceUserName', user)
    strs = strs + f.readline().replace('dataSourcePassword', password)
    strs = strs + f.readline().replace('targetServerName', target)
    strs = strs + f.readline().replace('driverClassName', driverClass)
    print(strs)
    line = f.readline()

    while line:
        line = f.readline()
        strs = strs + line
    f.close()

    w = open(tmpFile, "w")
    w.writelines(strs)
    w.close()

    subprocess.call([setDomainEnv])
    subprocess.call([execWlst, tmpFile])
