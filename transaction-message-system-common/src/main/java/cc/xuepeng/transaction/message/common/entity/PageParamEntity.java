package cc.xuepeng.transaction.message.common.entity;

/**
 * 分页参数实体类。
 *
 * @author xuepeng
 */
public final class PageParamEntity {

    /**
     * 当前页数。
     */
    private int pageNum;
    /**
     * 每页记录数。
     */
    private int numPerPage;

    /**
     * 构造函数。
     *
     * @param pageNum    当前页数。
     * @param numPerPage 每页记录数。
     */
    public PageParamEntity(int pageNum, int numPerPage) {
        this.pageNum = pageNum;
        this.numPerPage = numPerPage;
    }

    /**
     * @return 当前页数。
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * @return 每页记录数。
     */
    public int getNumPerPage() {
        return numPerPage;
    }
}
