package net.kuper.tz.core.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2016年11月12日 上午9:49:19
 */
public class ShiroUtils {

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static <T> T getUserEntity() {
        return (T) SecurityUtils.getSubject().getPrincipal();
    }

    public static <T> void setSessionAttribute(Object key, T value) {
        getSession().setAttribute(key, value);
    }

    public static <T> T getSessionAttribute(Object key) {
        return (T) getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }


}
