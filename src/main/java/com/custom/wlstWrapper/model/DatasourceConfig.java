package com.custom.wlstWrapper.model;

import lombok.*;

import java.io.Serializable;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DatasourceConfig implements Serializable {
    private String name;
    private String jndiName;
    private String dsURL;
    private String user;
    private String password;
    private String target;
    private String driverClass;
}
