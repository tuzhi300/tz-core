package net.kuper.tz.core.mybatis;

import java.lang.annotation.*;


/**
 * 数据权限过滤
 *
 * @author GaoYuan
 * @date 2018/4/17 下午2:40
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {
    /**
     * 过滤部门的字段名称
     *
     * @return
     */
    String value() default "dept_id";

    /**
     * 用户ID参数名称
     *
     * @return
     */
    String userFiled() default "userId";

}
