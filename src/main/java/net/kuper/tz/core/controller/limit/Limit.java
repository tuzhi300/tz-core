package net.kuper.tz.core.controller.limit;

import net.kuper.tz.core.constant.Time;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Limit {

    /**
     * 校验方式，默认：IP或USER
     *
     * @return
     */
    LimitType type() default LimitType.SESSION_OR_USER;

    /**
     * ip允许访问的次数，默认值200
     */
    int sessionCount() default 200;

    /**
     * ip时间段，单位为毫秒，默认值一分钟
     */
    long sessionTime() default Time.MIN;

    /**
     * user允许访问的次数，默认值200
     */
    int userCount() default 200;

    /**
     * user时间段，单位为毫秒，默认值一分钟
     */
    long userTime() default Time.MIN;

    /**
     * uri描述
     *
     * @return
     */
    String description() default "";

    /**
     * 超过限制时，错误提示
     *
     * @return
     */
    String error() default "请求次数超限";

    /**
     * 指定标识
     *
     * @return
     */
    String id() default "";


    /**
     * 失败处理Bean
     *
     * @return
     */
    String failBean() default "";

    /**
     * 自定义规则Bean
     *
     * @return
     */
    String rulerBean() default "";
}