adminUsername = "weblogicAdminUsername"
adminPassword = "weblogicAdminPassword"
adminURL = "t3://localhost:7001"
dsName = "weblogicDatasourceName"
dsJNDIName = "weblogicJndiName"
dsURL = "dataSourceUrl"
dsUsername = "dataSourceUserName"
dsPassword = "dataSourcePassword"
dsTargetName = "targetServerName"
dsDriverClass = "driverClassName"

print("*** Trying to Connect.... *****")
connect(adminUsername, adminPassword, adminURL)
print("*** Connected *****")

edit()
startEdit()

cd('/JDBCSystemResources/' + dsName + '/JDBCResource/' + dsName + '/JDBCDriverParams/' + dsName)
set('Password', dsPassword)

save()
activate()
