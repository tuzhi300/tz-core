package net.kuper.tz.core.controller.exception;

import net.kuper.tz.core.controller.ResCode;

/**
 * 自定义异常
 */
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = ResCode.ERROR.code;

    public ApiException(ResCode code) {
        super(code.msg);
        this.msg = code.msg;
        this.code = code.code;
    }

    public ApiException(Throwable t) {
        super(t);
        this.msg = ResCode.ERROR.msg;
    }

    public ApiException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ApiException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ApiException(Throwable t, String msg) {
        super(t);
        this.msg = msg;
    }

    public ApiException(Throwable e,String msg, int code) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
