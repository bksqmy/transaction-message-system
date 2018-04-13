package cc.xuepeng.transaction.message.core.dao.interceptor;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis的抽象拦截器。
 *
 * @author xuepeng
 */
public abstract class AbstractInterceptor implements Interceptor {

    /**
     * 复制MappedStatement对象。
     *
     * @param ms           MappedStatement
     * @param newSqlSource newSqlSource
     * @param isCount      isCount
     * @return MappedStatement对象。
     */
    protected MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource, boolean isCount) {
        Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource,
                ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        String[] s = ms.getKeyProperties();
        if (s == null) {
            builder.keyProperty(null);
        } else {
            builder.keyProperty(s[0]);
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        if (isCount) {
            List<ResultMap> resultMaps = new ArrayList<>();
            resultMaps.add(new ResultMap.Builder(ms.getConfiguration(), ms.getId(), Integer.class,
                    new ArrayList<ResultMapping>()).build());
            builder.resultMaps(resultMaps);
        } else {
            builder.resultMaps(ms.getResultMaps());
        }
        builder.cache(ms.getCache());
        return builder.build();
    }

    /**
     * BoundSqlSqlSource类。
     *
     * @author xuepeng
     */
    public static class BoundSqlSqlSource implements SqlSource {

        /**
         * BoundSql对象。
         */
        private BoundSql boundSql;

        /**
         * 构造函数。
         *
         * @param boundSql BoundSql对象。
         */
        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        /**
         * 获得BoundSql对象。
         *
         * @param parameterObject parameterObject对象。
         * @return BoundSql对象。
         */
        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }

    }

}
