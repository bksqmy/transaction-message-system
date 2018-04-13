package cc.xuepeng.transaction.message.common.entity;

/**
 * 通用实体类。
 *
 * @author xuepeng
 */
public abstract class BaseEntity {

    /**
     * 主键。
     */
    private String id;

    /**
     * 构造函数。
     */
    public BaseEntity() {
    }

    /**
     * 构造函数。
     *
     * @param id 主键。
     */
    public BaseEntity(String id) {
        this.id = id;
    }

    /**
     * @return 主键。
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键。
     *
     * @param id 主键。
     */
    public void setId(String id) {
        this.id = id;
    }

}
