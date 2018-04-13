package cc.xuepeng.transaction.message.common.entity;

import java.util.List;

/**
 * 分页数据实体类。
 *
 * @param <T> 继承自BaseEntity的实体类。
 * @author xuepeng
 */
public final class PageDataEntity<T extends BaseEntity> {

    /**
     * 当前页。
     */
    private final int currentPage;
    /**
     * 每页显示多少条。
     */
    private final int numPerPage;
    /**
     * 总记录数。
     */
    private final int totalCount;
    /**
     * 本页的数据列表。
     */
    private final List<T> recordList;
    /**
     * 总页数。
     */
    private final int pageCount;


    /**
     * 构造函数。
     *
     * @param currentPage 当前页。
     * @param numPerPage  每页显示行数。
     * @param totalCount  总页数。
     * @param recordList  本页的数据列表。
     */
    public PageDataEntity(int currentPage, int numPerPage, int totalCount, List<T> recordList) {
        this.currentPage = currentPage;
        this.numPerPage = numPerPage;
        this.totalCount = totalCount;
        this.recordList = recordList;
        // 计算总页码
        pageCount = (totalCount + numPerPage - 1) / numPerPage;
    }

    /**
     * @return 当前页。
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * @return 每页显示行数。
     */
    public int getNumPerPage() {
        return numPerPage;
    }

    /**
     * @return 总页数。
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @return 本页的数据列表。
     */
    public List<T> getRecordList() {
        return recordList;
    }

    /**
     * @return 总页数。
     */
    public int getPageCount() {
        return pageCount;
    }
}
