package net.kuper.tz.core.controller.log;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class LogInfo {
    private String servletPath;
    private String operation;
    private String method;
    private String params;
    private Long time;
    private String ip;
    private Date createDate;
    private Object userId;
    private String result;
    private String type;
    private String error;
    private String osName;
    private String osVersion;
    private String  browerName;
    private String  browerVersion;
    private String  browerType;
}
