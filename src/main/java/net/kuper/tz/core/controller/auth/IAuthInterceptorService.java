package net.kuper.tz.core.controller.auth;

import java.util.List;

public interface IAuthInterceptorService {

    /**
     * 白名单地址，支持通配符
     *
     * @return
     */
    List<String> whiteServletPath();

    /**
     * 用信息Bean类型
     *
     * @return
     */
    Class userBeanClass();

    /**
     * 用户Id 字段名
     *
     * @return
     */
    String userIdFaild();
    /**
     * 用户Id 字段名
     *
     * @return
     */
    Class userIdClass();


}
