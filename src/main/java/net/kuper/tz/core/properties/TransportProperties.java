package net.kuper.tz.core.properties;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class TransportProperties {
    /**
     * 自动注入请求属性，用户Id
     */
    @NotEmpty
    private String reqAttributeUserIdKey;
    /**
     * 请求头部参数token
     */
    @NotEmpty
    private String reqHeaderTokenKey;
    /**
     * 请求头部参数签名
     */
    @NotEmpty
    private String reqHeaderSignKey;
    /**
     * 请求客户端类型
     */
    @NotEmpty
    private String reqHeaderPlatformKey;
    /**
     * 请求客户端版本
     */
    @NotEmpty
    private String reqHeaderVersionKey;
    /**
     * 响应头部参数签名
     */
    @NotEmpty
    private String resHeaderSignKey;

    public String getReqAttributeUserIdKey() {
        return reqAttributeUserIdKey;
    }

    public void setReqAttributeUserIdKey(String reqAttributeUserIdKey) {
        this.reqAttributeUserIdKey = reqAttributeUserIdKey;
    }

    public String getReqHeaderTokenKey() {
        return reqHeaderTokenKey;
    }

    public void setReqHeaderTokenKey(String reqHeaderTokenKey) {
        this.reqHeaderTokenKey = reqHeaderTokenKey;
    }

    public String getReqHeaderSignKey() {
        return reqHeaderSignKey;
    }

    public void setReqHeaderSignKey(String reqHeaderSignKey) {
        this.reqHeaderSignKey = reqHeaderSignKey;
    }

    public String getReqHeaderPlatformKey() {
        return reqHeaderPlatformKey;
    }

    public void setReqHeaderPlatformKey(String reqHeaderPlatformKey) {
        this.reqHeaderPlatformKey = reqHeaderPlatformKey;
    }

    public String getReqHeaderVersionKey() {
        return reqHeaderVersionKey;
    }

    public void setReqHeaderVersionKey(String reqHeaderVersionKey) {
        this.reqHeaderVersionKey = reqHeaderVersionKey;
    }

    public String getResHeaderSignKey() {
        return resHeaderSignKey;
    }

    public void setResHeaderSignKey(String resHeaderSignKey) {
        this.resHeaderSignKey = resHeaderSignKey;
    }
}
