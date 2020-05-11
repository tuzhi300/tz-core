package net.kuper.tz.core.xss;

import net.kuper.tz.core.controller.exception.ApiException;
import net.kuper.tz.core.utils.StringUtils;

/**
 * SQL过滤
 */
public final class SQLFilter {

    /**
     * SQL注入过滤
     *
     * @param str 待验证的字符串
     */
    public static String sqlInject(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        // 去掉'|"|;|\字符
        str = str.replace("'", "");
        str = str.replace("\"", "");
        str = str.replace(";", "");
        str = str.replace("\\", "");

        // 转换成小写
        str = str.toLowerCase();

        // 非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};

        // 判断是否包含非法字符
        for (String keyword : keywords) {
            if (str.contains(keyword)) {
                throw new ApiException("包含非法字符");
            }
        }

        return str;
    }
}
