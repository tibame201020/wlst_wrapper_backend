package com.custom.wlstWrapper.controller;

import com.custom.wlstWrapper.model.ServerDeploy;
import com.custom.wlstWrapper.model.ServerJdbc;
import com.custom.wlstWrapper.service.WlstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class WlstController {
    @Autowired
    private WlstService wlstService;

    @RequestMapping("getServerJdbc")
    public ServerJdbc getServerJdbc() throws Exception {
        return wlstService.getJdbcConfig();
    }

    @RequestMapping("exePython")
    public boolean exePython(@RequestBody ServerJdbc serverJdbc) throws Exception {
        return wlstService.exePython(serverJdbc);
    }

    @RequestMapping("getServerDeploy")
    public ServerDeploy getServerDeploy() throws Exception {
        return wlstService.getServerDeploy();
    }

    @RequestMapping("exeDeploy")
    public boolean exeDeploy(@RequestBody ServerDeploy serverDeploy) throws Exception {
        return wlstService.exeDeploy(serverDeploy);
    }
}
