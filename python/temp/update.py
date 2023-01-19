adminUsername = "weblogic"
adminPassword = "weblogic123"
adminURL = "t3://localhost:7001"
dsName = "test"
dsJNDIName = "jdbc/pool/ORDBCBSCoreDSM122"
dsURL = "jdbc:oracle:thin:@tsrioda0.tsri.org.tw:1521:tsridb2"
dsUsername = "CBS"
dsPassword = "cbsscbss1122T"
dsTargetName = "AdminServer"
dsDriverClass = "oracle.jdbc.xa.client.OracleXADataSource"
print("*** Trying to Connect.... *****")
connect(adminUsername, adminPassword, adminURL)
print("*** Connected *****")

edit()
startEdit()

cd('/JDBCSystemResources/' + dsName + '/JDBCResource/' + dsName + '/JDBCDriverParams/' + dsName)
set('Password', dsPassword)

save()
activate()
