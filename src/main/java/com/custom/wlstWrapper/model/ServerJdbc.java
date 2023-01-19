package com.custom.wlstWrapper.model;

import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServerJdbc implements Serializable {
    private ServerConfig serverConfig;
    private DatasourceConfig[] jdbcConfigs;
    private boolean insertMode;
}
