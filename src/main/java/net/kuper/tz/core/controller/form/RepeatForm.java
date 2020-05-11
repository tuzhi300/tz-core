package net.kuper.tz.core.controller.form;


import net.kuper.tz.core.constant.Time;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 一个用户 相同url 同时提交 相同数据 验证
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatForm {
    /**
     * 间隔时间，默认5秒
     *
     * @return
     */
    long interval() default 3 * Time.SEC;

    /**
     * 错误提示
     *
     * @return
     */
    String error() default "请慢一点哦，我脑子转不过来了";

}
