package net.kuper.tz.core.controller.log;

public enum LogType {
    /**
     * 登录
     */
    LOGIN("login"),
    /**
     * 登出
     */
    LOGOUT("logout"),
    /**
     * 保存
     */
    SAVE("save"),
    /**
     * 修改
     */
    UPDATE("update"),
    /**
     * 删除
     */
    DELETE("delete"),
    /**
     * 查询
     */
    QUERY("query"),
    /**
     * 文件上传
     */
    UPLOAD("upload"),
    /**
     * 文件上传
     */
    DOWNLOAD("download"),
    /**
     * 复杂业务操作
     */
    OPERATOR("operator");

    private String type;

    LogType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
