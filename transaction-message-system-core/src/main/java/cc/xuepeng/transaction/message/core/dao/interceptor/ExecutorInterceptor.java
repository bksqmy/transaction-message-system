package cc.xuepeng.transaction.message.core.dao.interceptor;

import cc.xuepeng.transaction.message.core.dao.dialect.Dialect;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * Mybatis查询拦截器。
 *
 * @author xuepeng
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
        RowBounds.class, ResultHandler.class})})
public class ExecutorInterceptor extends AbstractInterceptor {
    /**
     * 映射命令索引。
     */
    private static final int MAPPED_STATEMENT_INDEX = 0;
    /**
     * 参数索引。
     */
    private static final int PARAMETER_INDEX = 1;
    /**
     * 分页索引。
     */
    private static final int ROWBOUNDS_INDEX = 2;
    /**
     * 排序关键字。
     */
    private static final String ORDER_BY = "order by";
    /**
     * 方言对象。
     */
    private Dialect dialect;

    /**
     * 加载方言对象。
     *
     * @param properties 属性配置对象。
     */
    @Override
    public void setProperties(Properties properties) {
        String dialectClass = properties.getProperty("dialectClass");
        try {
            dialect = (Dialect) Class.forName(dialectClass).newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("方言加载属性异常");
        }

    }

    /**
     * 查询拦截器函数。
     *
     * @param invocation Invocation对象。
     * @return 拦截结果。
     * @throws Throwable 当方法出错时抛出该异常。
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        processIntercept(invocation.getArgs());
        return invocation.proceed();
    }

    /**
     * 拦截查询请求并处理。
     *
     * @param queryArgs 查询参数。
     */
    private void processIntercept(final Object[] queryArgs) {
        MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        Object parameter = queryArgs[PARAMETER_INDEX];
        final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();
        if (dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
            BoundSql boundSql = ms.getBoundSql(parameter);
            String sql = boundSql.getSql().replaceAll("\\s{2,}", " ").trim();
            if (dialect.supportsLimitOffset()) {
                sql = dialect.createLimitString(sql, offset, limit);
                offset = RowBounds.NO_ROW_OFFSET;
            } else {
                sql = dialect.createLimitString(sql, 0, limit);
            }

            limit = RowBounds.NO_ROW_LIMIT;
            queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
            BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
                    boundSql.getParameterObject());
            MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql), false);
            queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
        } else if (parameter instanceof CountParameter) {
            // 获取总数
            parameter = ((CountParameter) parameter).getParameter();
            BoundSql boundSql = ms.getBoundSql(parameter);
            String sql = boundSql.getSql().replaceAll("\\s{2,}", " ").replace(" FROM", " from")
                    .replace("ORDER BY", ORDER_BY).replace("GROUP BY", ORDER_BY).trim();
            if (sql.split("from").length > 2 || sql.split(ORDER_BY).length > 2 || sql.indexOf(ORDER_BY) > -1) {
                sql = "select count(1) from (" + sql + ") tmp";
            } else {
                int fromIndex = sql.indexOf(" from");
                sql = "select count(1)" + sql.substring(fromIndex);
                int orderByIndex = sql.indexOf(ORDER_BY);
                if (orderByIndex > 0) {
                    sql = sql.substring(0, orderByIndex);
                }
            }
            BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
                    boundSql.getParameterObject());
            MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql), true);
            queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
            queryArgs[PARAMETER_INDEX] = parameter;
        }
    }

    /**
     * 设置插件。
     *
     * @param target 目标对象。
     * @return 设置插件。
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 数量参数对象。
     *
     * @author xuepeng
     */
    public static class CountParameter {

        /**
         * 参数对象。
         */
        private Object parameter;

        /**
         * 构造函数。
         *
         * @param parameter 参数对象。
         */
        public CountParameter(Object parameter) {
            this.parameter = parameter;
        }

        /**
         * @return 参数对象。
         */
        public Object getParameter() {
            return parameter;
        }
    }

}
