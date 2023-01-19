package com.custom.wlstWrapper.model;

import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServerDeploy implements Serializable {
    private ServerConfig serverConfig;
    private DeployConfig[] deployConfigs;
}
