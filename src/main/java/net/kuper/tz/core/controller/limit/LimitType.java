package net.kuper.tz.core.controller.limit;

public enum LimitType {

    /**
     * ip
     */
    SESSION("session"),
    /**
     * user 必须是用户已登录
     */
    USER("user"),
    /**
     * IP并且USER
     */
    SESSION_AND_USER("session_and_user"),
    /**
     * IP或者USER
     */
    SESSION_OR_USER("session_or_user");

    private String type;

    LimitType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
