package com.custom.wlstWrapper.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ServerConfig implements Serializable {
    private String adminURL;
    private String adminUsername;
    private String adminPassword;
    private String setDomainEnv;
    private String execWlst;
    private String deployPath;
}
