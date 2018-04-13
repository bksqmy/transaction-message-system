package cc.xuepeng.account.entity;

import cc.xuepeng.transaction.message.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户信息的实体类。
 *
 * @author xuepeng
 */
public final class AccountInfo extends BaseEntity {

    /**
     * 商户编号。
     */
    private String merchantId;
    /**
     * 订单编号。
     */
    private String orderId;
    /**
     * 余额。
     */
    private BigDecimal balance;
    /**
     * 税款。
     */
    private BigDecimal taxes;
    /**
     * 总计。
     */
    private BigDecimal total;
    /**
     * 创建时间。
     */
    private LocalDateTime createTime;
    /**
     * 最后修改时间。
     */
    private LocalDateTime modifyTime;

    /**
     * @return 商户编号。
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * 设置商户编号。
     *
     * @param merchantId 商户编号。
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * @return 订单编号。
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单编号。
     *
     * @param orderId 订单编号。
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return 余额。
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置余额。
     *
     * @param balance 余额。
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * @return 税款。
     */
    public BigDecimal getTaxes() {
        return taxes;
    }

    /**
     * 设置税款。
     *
     * @param taxes 税款。
     */
    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    /**
     * @return 总计。
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * 设置总计。
     *
     * @param total 总计。
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return 创建时间。
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间。
     *
     * @param createTime 创建时间。
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * @return 最后修改时间。
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置最后修改时间。
     *
     * @param modifyTime 最后修改时间。
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

}
