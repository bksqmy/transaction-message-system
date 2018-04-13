package cc.xuepeng.transaction.message.service;

import cc.xuepeng.transaction.message.entity.TransactionMessage;

/**
 * 可靠消息服务接口。
 *
 * @author xuepeng
 */
public interface TransactionMessageService {

    /**
     * 预存储消息。
     *
     * @param transactionMessage 消息实体类。
     * @return 消息是否预存储成功。
     */
    boolean saveAndWaitingConfirm(TransactionMessage transactionMessage);

    /**
     * 确认并向MQ中发送消息。
     *
     * @param messageId 消息Id。
     */
    void confirmAndSendById(String messageId);

    /**
     * 直接确认并向MQ中发送消息，忽略预存储。
     *
     * @param transactionMessage 消息实体类。
     * @return 消息是否发送成功。
     */
    boolean saveAndSend(TransactionMessage transactionMessage);

    /**
     * 直接向MQ中发送消息。
     *
     * @param transactionMessage 消息实体类。
     */
    void directAndSend(TransactionMessage transactionMessage);

    /**
     * 根据消息Id重发消息。
     *
     * @param messageId 消息Id。
     */
    void reSendByMessageId(String messageId);

    /**
     * 根据消息Id标记消息状态为死亡。
     *
     * @param messageId 消息Id。
     */
    boolean setDeadById(String messageId);

    /**
     * 根据消息Id获取消息。
     *
     * @param messageId 消息Id。
     * @return 消息实体类。
     */
    TransactionMessage getByMessageId(String messageId);

    /**
     * 根据队列名称批量发送死信队列中的消息。
     *
     * @param queueName 消息名称。
     */
    void reSendDeadByQueueName(String queueName);

    /**
     * 根据消息Id删除消息。
     *
     * @param messageId 消息Id。
     */
    void deleteById(String messageId);

}
