package com.custom.wlstWrapper.service.impl;

import com.custom.wlstWrapper.model.*;
import com.custom.wlstWrapper.service.WlstService;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


@Service
public class WlstServiceImpl implements WlstService {

    public String exeCmd = "cmd.exe /c start windows_start.py .\\data\\serverConfig.json .\\data\\datasource.json %s.py";
    public String pythonResourceBasePath = "python/";
    public String serverConfigPath = pythonResourceBasePath + "data/serverConfig.json";
    public String datasourcePath = pythonResourceBasePath + "data/datasource.json";

    private String deployBat = "cmd.exe /c start deploy.bat %s %s %s %s %s %s %s";
    public String deployConfigPath = pythonResourceBasePath + "data/deploy.json";


    @Override
    public ServerJdbc getJdbcConfig() throws Exception {
        ServerConfig serverConfig = getServerConfig();
        DatasourceConfig[] jdbcData = getJdbcData();

        return new ServerJdbc(serverConfig, jdbcData, true);
    }

    @Override
    public boolean exePython(ServerJdbc serverJdbc) throws Exception {
        String serverConfigContent = new Gson().toJson(serverJdbc.getServerConfig());
        String jdbcDataContent = new Gson().toJson(serverJdbc.getJdbcConfigs());

        if (!(writeFile(serverConfigContent, serverConfigPath) && writeFile(jdbcDataContent, datasourcePath))) {
            return false;
        }

        return exec(getExeCmd(serverJdbc.isInsertMode()));
    }

    @Override
    public ServerDeploy getServerDeploy() throws Exception {
        return new ServerDeploy(getServerConfig(), getDeployConfig());
    }

    @Override
    public boolean exeDeploy(ServerDeploy serverDeploy) throws Exception {

        String serverConfigContent = new Gson().toJson(serverDeploy.getServerConfig());
        String deployContent = new Gson().toJson(serverDeploy.getDeployConfigs());

        if (!(writeFile(serverConfigContent, serverConfigPath) && writeFile(deployContent, deployConfigPath))) {
            return false;
        }

        for (DeployConfig deployConfig:serverDeploy.getDeployConfigs()) {
            if (!exec(getDeployBat(serverDeploy.getServerConfig(), deployConfig))) {
                continue;
            }
        }

        return true;
    }

    private boolean exec(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command, null, new File(pythonResourceBasePath));
            readProcess(process);
            process.destroy();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String readProcess(Process process) throws Exception {
        String normalMsg = getMessageFromInputStream(process.getInputStream());

        if (StringUtils.isNotBlank(normalMsg)) {
            return normalMsg;
        } else {
            return getMessageFromInputStream(process.getErrorStream());
        }
    }

    private String getMessageFromInputStream(InputStream inputStream) throws Exception {
        String message = IOUtils.toString(inputStream, "UTF-8");
        inputStream.close();
        return message;
    }

    private boolean writeFile(String content, String targetPath) {
        try {
            FileUtils.writeStringToFile(ResourceUtils.getFile(targetPath), content, StandardCharsets.UTF_8);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private DatasourceConfig[] getJdbcData() throws Exception {
        String content = FileUtils.readFileToString(ResourceUtils.getFile(datasourcePath), StandardCharsets.UTF_8);
        return new Gson().fromJson(content, DatasourceConfig[].class);
    }

    private ServerConfig getServerConfig() throws Exception {
        String content = FileUtils.readFileToString(ResourceUtils.getFile(serverConfigPath), StandardCharsets.UTF_8);
        return new Gson().fromJson(content, ServerConfig.class);
    }

    private DeployConfig[] getDeployConfig() throws Exception {
        String content = FileUtils.readFileToString(ResourceUtils.getFile(deployConfigPath), StandardCharsets.UTF_8);
        return new Gson().fromJson(content, DeployConfig[].class);
    }

    private String getExeCmd(boolean isInsertMode) {
        if (isInsertMode) {
            return String.format(exeCmd, "insert");
        }
        return String.format(exeCmd, "update");
    }

    private String getDeployBat(ServerConfig serverConfig, DeployConfig deployConfig) {
        return String.format(
                deployBat,
                serverConfig.getAdminURL(),
                serverConfig.getAdminUsername(),
                serverConfig.getAdminPassword(),
                serverConfig.getDeployPath(),
                deployConfig.getProject(),
                deployConfig.getWarPath(),
                deployConfig.getTarget()
        );
    }
}
