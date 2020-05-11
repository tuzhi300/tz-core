package net.kuper.tz.core.mybatis;

import net.kuper.tz.core.mybatis.utils.PermissionUtils;
import net.kuper.tz.core.utils.ReflectUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;

/**
 * 数据权限过滤，结合PageHelper实现
 */
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class DataPermissionInterceptor implements Interceptor {

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(DataPermissionInterceptor.class);

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        DataPermission dataPermission = PermissionUtils.getPermissionByDelegate(ms);
        if (dataPermission == null) {
            return invocation.proceed();
        }
        Object paramsObj = args[1];
        Long id; // 用户ID
        if (paramsObj instanceof Integer) {
            id = (Long) paramsObj;
        } else if (paramsObj instanceof Long) {
            id = (Long) paramsObj;
        } else if (paramsObj instanceof Map) {
            id = (Long) ((Map) paramsObj).get(dataPermission.userFiled());
        } else {
            id = ReflectUtils.reflect(paramsObj).field(dataPermission.userFiled()).get();
        }
        if (id == null) {
            throw new RuntimeException("You must set current user id in the params");
        }
        BoundSql boundSql;
        if (args.length == 4) {
            //4 个参数时
            boundSql = ms.getBoundSql(paramsObj);
        } else {
            //6 个参数时
            boundSql = (BoundSql) args[5];
        }
        String sql = PermissionUtils.permissionSql(dataPermission.value(), id, boundSql.getSql());
        MappedStatement newStatement = newMappedStatement(ms, new BoundSqlSqlSource(boundSql));
        MetaObject msObject = MetaObject.forObject(newStatement, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        msObject.setValue("sqlSource.boundSql.sql", sql);
        args[0] = newStatement;
        return invocation.proceed();
    }

    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource())
                .fetchSize(ms.getFetchSize())
                .statementType(ms.getStatementType())
                .keyGenerator(ms.getKeyGenerator())
                .timeout(ms.getTimeout())
                .parameterMap(ms.getParameterMap())
                .resultMaps(ms.getResultMaps())
                .resultSetType(ms.getResultSetType())
                .cache(ms.getCache())
                .flushCacheRequired(ms.isFlushCacheRequired())
                .useCache(ms.isUseCache())
                .databaseId(ms.getDatabaseId())
                .lang(ms.getLang())
                .resultOrdered(ms.isResultOrdered());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        return builder.build();
    }


    //    定义一个内部辅助类，作用是包装sq
    class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

}
