package com.custom.wlstWrapper.model;

import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeployConfig implements Serializable {
    private String project;
    private String warPath;
    private String target;
}
