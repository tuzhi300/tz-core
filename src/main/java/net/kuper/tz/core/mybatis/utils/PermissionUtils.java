package net.kuper.tz.core.mybatis.utils;

import net.kuper.tz.core.mybatis.DataPermission;
import net.kuper.tz.core.utils.SpringUtils;
import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.Method;

/**
 * 自定义权限相关工具类
 *
 * @author GaoYuan
 * @date 2018/4/17 上午11:45
 */
public class PermissionUtils {

    /**
     * 根据 StatementHandler 获取 注解对象
     *
     * @author GaoYuan
     * @date 2018/4/17 上午11:45
     */
    public static DataPermission getPermissionByDelegate(MappedStatement mappedStatement) {
        DataPermission dataPermission = null;
        try {
            String id = mappedStatement.getId();
            String className = id.substring(0, id.lastIndexOf("."));
            String methodName = id.substring(id.lastIndexOf(".") + 1, id.length());
            final Class cls = Class.forName(className);
            final Method[] method = cls.getMethods();
            for (Method me : method) {
                if (me.getName().equals(methodName) && me.isAnnotationPresent(DataPermission.class)) {
                    dataPermission = me.getAnnotation(DataPermission.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataPermission;
    }


    /**
     * 权限sql包装
     *
     * @author GaoYuan
     * @date 2018/4/17 上午9:51
     */
    public static String permissionSql(String column, Long userId, String sql) {
        String roleId = SpringUtils.applicationContext.getEnvironment().getProperty("tz.admin.super-role-id");
        if (roleId == null) {
            throw new RuntimeException("please add tz.admin.super-role-id in your config");
        }

        String userRole = "select sur.role_id " +
                "from sys_user_role sur " +
                "where sur.user_id = 1 " +
                "and sur.delete_time is null ";
        String userDept = "select sud.dept_id " +
                "from sys_user_data sud " +
                "where sud.disable = 0 " +
                "and sud.user_id = " + userId;
        String roleDept = "select sd.id " +
                "from sys_dept  sd " +
                "left join sys_role_data srd on sd.id = srd.dept_id " +
                "left join sys_user_data s on sd.id = s.dept_id " +
                "where srd.role_id in ( " +
                userRole +
                ")" +
                "and s.disable != 1 " +
                "or " + roleId + " in( " +
                userRole +
                ")";

        String deptSql = userDept + " union all " + roleDept;
        StringBuilder subSql = new StringBuilder("select * from (")
                .append(sql)
                .append(") t where t.")
                .append(column)
                .append(" in (")
                .append(deptSql)
                .append(")");
        return subSql.toString();
    }


}
