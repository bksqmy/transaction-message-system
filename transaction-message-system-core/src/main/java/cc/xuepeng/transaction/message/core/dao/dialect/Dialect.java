package cc.xuepeng.transaction.message.core.dao.dialect;

/**
 * 数据库方言接口。
 *
 * @author xuepeng
 */
public interface Dialect {

    /**
     * @return 是否支持Limit功能。
     */
    default boolean supportsLimit() {
        return false;
    }

    /**
     * @return 是否支持LimitOffset功能。
     */
    default boolean supportsLimitOffset() {
        return supportsLimit();
    }

    /**
     * 创建limit的SQL语句。
     *
     * @param sql    SQL语句。
     * @param offset 偏移量。
     * @param limit  界限。
     * @return limit的SQL语句。
     */
    default String createLimitString(String sql, int offset, int limit) {
        return createLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
    }


    /**
     * 创建limit的SQL语句。
     *
     * @param sql               SQL语句。
     * @param offset            偏移量。
     * @param offsetPlaceholder 偏移量占位符。
     * @param limit             界限。
     * @param limitPlaceholder  界限占位符。
     * @return limit的SQL语句。
     */
    String createLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder);

}
