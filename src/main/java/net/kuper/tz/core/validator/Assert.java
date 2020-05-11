
package net.kuper.tz.core.validator;


import net.kuper.tz.core.controller.exception.ApiException;
import net.kuper.tz.core.utils.RegexUtils;
import net.kuper.tz.core.utils.StringUtils;

public final class Assert {

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new ApiException(message);
        }
    }

    public static void isEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new ApiException(message);
        }
    }

    public static void isMobileSimple(String mobile, String message) {
        if (RegexUtils.isMobileSimple(mobile)) {
            throw new ApiException(message);
        }
    }

    public static void isMobileExact(String mobile, String message) {
        if (RegexUtils.isMobileExact(mobile)) {
            throw new ApiException(message);
        }
    }

    public static void isEmail(String email, String message) {
        if (RegexUtils.isEmail(email)) {
            throw new ApiException(message);
        }
    }

    public static void isIDCard18(String idCard, String message) {
        if (RegexUtils.isIDCard18(idCard)) {
            throw new ApiException(message);
        }
    }

    public static void isUserName(String userName, String message) {
        if (RegexUtils.isUsername(userName)) {
            throw new ApiException(message);
        }
    }

    public static void isTel(String tel, String message) {
        if (RegexUtils.isTel(tel)) {
            throw new ApiException(message);
        }
    }
}
