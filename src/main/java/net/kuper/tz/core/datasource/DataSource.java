package net.kuper.tz.core.datasource;

import java.lang.annotation.*;

/**
 * 多数据源注解
 * @author geYang
 * @date 2018-05-14
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    String value() default DataSourceNames.ONE;
}
