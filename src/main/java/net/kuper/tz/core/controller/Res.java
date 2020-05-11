package net.kuper.tz.core.controller;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 返回数据
 */
@Getter
@Setter
public class Res<T> implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态码")
    private int code;
    @ApiModelProperty(value = "提示是消息")
    private String msg;
    @ApiModelProperty(value = "业务数据")
    private T data;

    public Res() {
        this.code = ResCode.SUCCESS.code;
        this.msg = ResCode.SUCCESS.msg;
    }


    public static Res code(ResCode code) {
        Res res = new Res();
        res.setResCode(code);
        return res;
    }

    public static Res error() {
        return error(ResCode.ERROR.msg);
    }

    public static Res error(String msg) {
        return error(ResCode.ERROR.code, msg);
    }

    public static Res error(int code, String msg) {
        Res res = new Res();
        res.setCode(code);
        res.setMsg(msg);
        return res;
    }

    public static Res ok(String msg) {
        Res res = new Res();
        res.setMsg(msg);
        return res;
    }

    public static <T> Res<T> ok(T t) {
        Res<T> res = new Res<T>();
        res.setData(t);
        return res;
    }

    public static Res ok() {
        return new Res();
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setResCode(ResCode code) {
        this.code = code.code;
        this.msg = code.msg;
    }


}
