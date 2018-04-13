package cc.xuepeng.trade.entity;

import cc.xuepeng.trade.enums.OrderStatus;
import cc.xuepeng.transaction.message.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单信息的实体类。
 *
 * @author xuepeng
 */
public final class OrderInfo extends BaseEntity {

    /**
     * 订单编号。
     */
    private String orderId;
    /**
     * 订单状态。
     */
    private OrderStatus orderStatus;
    /**
     * 订单金额。
     */
    private BigDecimal orderAmount;
    /**
     * 客户编号。
     */
    private String customerId;
    /**
     * 商户编号。
     */
    private String merchantId;
    /**
     * 创建时间。
     */
    private LocalDateTime createTime;
    /**
     * 最后修改时间。
     */
    private LocalDateTime modifyTime;

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
     * @return 订单状态。
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置订单状态。
     *
     * @param orderStatus 订单状态。
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return 订单金额。
     */
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    /**
     * 设置订单金额。
     *
     * @param orderAmount 订单金额。
     */
    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * @return 客户编号。
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * 设置客户编号。
     *
     * @param customerId 客户编号。
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

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
