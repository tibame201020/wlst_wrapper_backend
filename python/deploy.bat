@SET serverUrl=%1%
@SET serverUser=%2%
@SET serverPwd=%3%
@SET CLASSPATH=%4%

@SET project=%5%
@SET warFileSource=%6%
@SET target=%7%

@echo off
Goto info

:info
@echo ########## config ##########
@echo project:%project%
@echo source:%warFileSource%
@echo serverUrl:%serverUrl%
@echo deployTo:%target%
@echo ############################

goto deploy

:deploy
call java weblogic.Deployer -adminurl %serverUrl% -username %serverUser% -password %serverPwd% -name %project% -undeploy
call java weblogic.Deployer -adminurl %serverUrl% -username %serverUser% -password %serverPwd% -name %project% -source %warFileSource% -targets %target% -upload -deploy
exit