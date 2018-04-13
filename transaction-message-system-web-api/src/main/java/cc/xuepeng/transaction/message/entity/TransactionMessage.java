package cc.xuepeng.transaction.message.entity;

import cc.xuepeng.transaction.message.common.entity.BaseEntity;
import cc.xuepeng.transaction.message.enums.MessageStatus;

import java.time.LocalDateTime;

/**
 * 可靠消息服务的消息实体类。
 *
 * @author xuepeng
 */
public final class TransactionMessage extends BaseEntity {

    /**
     * 消息Id。
     */
    private String messageId;
    /**
     * 消息内容。
     */
    private String messageBody;
    /**
     * 消息状态。
     */
    private MessageStatus messageStatus;
    /**
     * 是否已进入死信队列。
     */
    private Boolean isDead;
    /**
     * 重发次数。
     */
    private Integer reSendTime;
    /**
     * 队列名称。
     */
    private String consumerQueue;
    /**
     * 创建时间。
     */
    private LocalDateTime createTime;
    /**
     * 最后修改时间。
     */
    private LocalDateTime modifyTime;

    /**
     * 构造函数。
     */
    public TransactionMessage() {
    }

    /**
     * 构造函数。
     *
     * @param id            主键。
     * @param messageId     消息Id。
     * @param messageBody   消息内容。
     * @param messageStatus 消息状态。
     * @param isDead        是否已进入死信队列。
     * @param reSendTime    重发次数。
     * @param consumerQueue 队列名称。
     */
    public TransactionMessage(String id, String messageId, String messageBody, MessageStatus messageStatus, Boolean isDead, Integer reSendTime, String consumerQueue) {
        super(id);
        this.messageId = messageId;
        this.messageBody = messageBody;
        this.messageStatus = messageStatus;
        this.isDead = isDead;
        this.reSendTime = reSendTime;
        this.consumerQueue = consumerQueue;
    }

    /**
     * @return 消息Id。
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * 设置消息Id。
     *
     * @param messageId 消息Id。
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return 消息内容。
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     * 设置消息内容。
     *
     * @param messageBody 消息内容。
     */
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    /**
     * @return 消息状态。
     */
    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    /**
     * 设置消息状态。
     *
     * @param messageStatus 消息状态。
     */
    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    /**
     * @return 是否死亡。
     */
    public Boolean getDead() {
        return isDead;
    }

    /**
     * 设置是否死亡。
     *
     * @param dead 是否死亡。
     */
    public void setDead(Boolean dead) {
        isDead = dead;
    }

    /**
     * @return 重发次数。
     */
    public Integer getReSendTime() {
        return reSendTime;
    }

    /**
     * 设置重发次数。
     *
     * @param reSendTime 重发次数。
     */
    public void setReSendTime(Integer reSendTime) {
        this.reSendTime = reSendTime;
    }

    /**
     * @return 队列名称。
     */
    public String getConsumerQueue() {
        return consumerQueue;
    }

    /**
     * 设置队列名称。
     *
     * @param consumerQueue 队列名称。
     */
    public void setConsumerQueue(String consumerQueue) {
        this.consumerQueue = consumerQueue;
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

    /**
     * 从发次数自增一次。
     */
    public void addSendTimes() {
        reSendTime++;
    }

}
