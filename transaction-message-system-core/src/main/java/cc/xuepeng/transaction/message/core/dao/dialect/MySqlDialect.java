package cc.xuepeng.transaction.message.core.dao.dialect;

/**
 * MySql数据库方言接口。
 *
 * @author xuepeng
 */
public final class MySqlDialect implements Dialect {

    /**
     * 创建limit的SQL语句。
     *
     * @param sql    SQL语句。
     * @param offset 偏移量。
     * @param limit  界限。
     * @return limit的SQL语句。
     */
    @Override
    public String createLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        StringBuilder stringBuilder = new StringBuilder(sql);
        stringBuilder.append(" limit ");
        if (offset > 0) {
            stringBuilder.append(offsetPlaceholder);
            stringBuilder.append(',');
        }
        stringBuilder.append(limitPlaceholder);
        return stringBuilder.toString();
    }
}
