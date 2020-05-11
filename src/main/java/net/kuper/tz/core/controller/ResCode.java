package net.kuper.tz.core.controller;

/**
 * Created by chenchunping on 2018/6/22.
 */
public enum ResCode {

    /*常用状态码 1000-1999 ，默认定义*/
    SUCCESS(1000, "success"),
    /* 系统错误 2000-2999*/
    ERROR(2000, "未知错误"),
    ERROR_SYS_AUTH_UNLOGIN(2001, "请登录"),
    ERROR_SYS_AUTH_FAIL(2002, "登录过期，请重新登录"),
    ERROR_SYS_REPEAT_DATA(2003, "请勿重复提交数据"),
    ERROR_SYS_LIMIT_REQUEST(2004, "操作频繁，休息一下再来"),
    ERROR_SYS_LIMIT_REQUEST_BY_USER(2005, "操作频繁，请稍后再试。"),
    ERROR_SYS_PERMISSION(2006, "没有权限，请联系管理员");

    /*业务**错误 3000-3999*/
    /*业务**错误 4000-4999*/

    ResCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code;
    public String msg;


}
