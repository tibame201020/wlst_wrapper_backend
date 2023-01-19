package com.custom.wlstWrapper.service;

import com.custom.wlstWrapper.model.*;

public interface WlstService {
    ServerJdbc getJdbcConfig() throws Exception;
    boolean exePython(ServerJdbc serverJdbc) throws Exception;
    ServerDeploy getServerDeploy() throws Exception;
    boolean exeDeploy(ServerDeploy serverDeploy) throws Exception;
}
