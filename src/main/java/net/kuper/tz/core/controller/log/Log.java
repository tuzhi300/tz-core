package net.kuper.tz.core.controller.log;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 要执行的操作类型比如：add操作
     **/
    LogType type();

    /**
     * 要执行的具体操作比如：添加用户
     **/
    String value();

    /**
     * 保存参数
     *
     * @return
     */
    boolean saveParam() default true;

    /**
     * 保存返回结果 默认否
     *
     * @return
     */
    boolean saveResult() default true;
}
