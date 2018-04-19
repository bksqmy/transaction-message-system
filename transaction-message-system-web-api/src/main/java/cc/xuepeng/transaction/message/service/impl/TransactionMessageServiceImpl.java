package cc.xuepeng.transaction.message.service.impl;

import cc.xuepeng.transaction.message.common.entity.PageDataEntity;
import cc.xuepeng.transaction.message.common.entity.PageParamEntity;
import cc.xuepeng.transaction.message.dao.TransactionMessageDao;
import cc.xuepeng.transaction.message.entity.TransactionMessage;
import cc.xuepeng.transaction.message.enums.MessageStatus;
import cc.xuepeng.transaction.message.service.TransactionMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 可靠消息服务实现类。
 *
 * @author xuepeng
 */
@Service("transactionMessageService")
public class TransactionMessageServiceImpl implements TransactionMessageService {

    /**
     * 消息对象的数据访问接口。
     */
    @Autowired
    private TransactionMessageDao transactionMessageDao;
    /**
     * RabbitMQ访问类。
     */
    @Autowired
    private AmqpTemplate amqpTemplate;
    /**
     * 最大发送次数。
     */
    @Value("${transactionMessage.maxSentTime}")
    private String maxSendTime;
    /**
     * 批量发送的次数
     */
    @Value("${transactionMessage.batchSize}")
    private String batchSize;

    /**
     * 消息Id不能为Null的提示信息。
     */
    private static final String TIP_MESSAGE_ID_NULL = "MessageId不能为Null。";
    /**
     * TransactionMessage对象不能为Null的提示信息。
     */
    private static final String TIP_MESSAGE_NULL = "TransactionMessage对象不能为Null。";
    /**
     * 要发送的消费队列名称不能为Null的提示信息。
     */
    private static final String TIP_QUEUE_NAME_NULL = "要发送的消费队列名称不能为Null。";

    /**
     * 预存储消息。
     *
     * @param transactionMessage 消息实体类。
     * @return 消息是否预存储成功。
     */
    @Override
    public boolean saveAndWaitingConfirm(final TransactionMessage transactionMessage) {
        if (transactionMessage == null) {
            throw new NullPointerException(TIP_MESSAGE_NULL);
        }
        if (StringUtils.isEmpty(transactionMessage.getConsumerQueue())) {
            throw new NullPointerException(TIP_QUEUE_NAME_NULL);
        }
        transactionMessage.setId(UUID.randomUUID().toString());
        transactionMessage.setCreateTime(LocalDateTime.now());
        transactionMessage.setModifyTime(LocalDateTime.now());
        transactionMessage.setMessageStatus(MessageStatus.WAITING_CONFIRM);
        transactionMessage.setDead(Boolean.FALSE);
        transactionMessage.setReSendTime(0);
        int result = transactionMessageDao.insert(transactionMessage);
        return result > 0;
    }

    /**
     * 确认并向MQ中发送消息。
     *
     * @param messageId 消息Id。
     */
    @Override
    public void confirmAndSendById(final String messageId) {
        if (StringUtils.isEmpty(messageId)) {
            throw new NullPointerException(TIP_MESSAGE_ID_NULL);
        }
        TransactionMessage transactionMessage = getByMessageId(messageId);
        if (transactionMessage == null) {
            throw new NullPointerException(TIP_MESSAGE_NULL);
        }
        transactionMessage.setMessageStatus(MessageStatus.SENDING);
        transactionMessage.setModifyTime(LocalDateTime.now());
        final int result = transactionMessageDao.update(transactionMessage);
        if (result > 0) {
            amqpTemplate.convertAndSend(transactionMessage.getConsumerQueue(), transactionMessage.getMessageBody());
        }
    }

    /**
     * 直接确认并向MQ中发送消息，忽略预存储。
     *
     * @param transactionMessage 消息实体类。
     * @return 消息是否发送成功。
     */
    @Override
    public boolean saveAndSend(final TransactionMessage transactionMessage) {
        if (transactionMessage == null) {
            throw new NullPointerException(TIP_MESSAGE_NULL);
        }
        if (StringUtils.isEmpty(transactionMessage.getConsumerQueue())) {
            throw new NullPointerException(TIP_QUEUE_NAME_NULL);
        }
        transactionMessage.setCreateTime(LocalDateTime.now());
        transactionMessage.setModifyTime(LocalDateTime.now());
        transactionMessage.setMessageStatus(MessageStatus.SENDING);
        transactionMessage.setDead(Boolean.FALSE);
        transactionMessage.setReSendTime(0);
        final int result = transactionMessageDao.insert(transactionMessage);
        if (result > 0) {
            amqpTemplate.convertAndSend(transactionMessage.getConsumerQueue(), transactionMessage.getMessageBody());
        }
        return result > 0;
    }

    /**
     * 直接向MQ中发送消息。
     *
     * @param transactionMessage 消息实体类。
     */
    @Override
    public void directAndSend(final TransactionMessage transactionMessage) {
        if (transactionMessage == null) {
            throw new NullPointerException(TIP_MESSAGE_NULL);
        }
        if (StringUtils.isEmpty(transactionMessage.getConsumerQueue())) {
            throw new NullPointerException(TIP_QUEUE_NAME_NULL);
        }
        amqpTemplate.convertAndSend(transactionMessage.getConsumerQueue(), transactionMessage.getMessageBody());
    }

    /**
     * 根据消息Id重发消息。
     *
     * @param messageId 消息Id。
     */
    @Override
    public void reSendByMessageId(String messageId) {
        if (StringUtils.isEmpty(messageId)) {
            throw new NullPointerException(TIP_MESSAGE_ID_NULL);
        }
        TransactionMessage transactionMessage = getByMessageId(messageId);
        if (transactionMessage == null) {
            throw new NullPointerException(TIP_MESSAGE_NULL);
        }
        final int maxTime = Integer.parseInt(maxSendTime);
        if (transactionMessage.getReSendTime() >= maxTime) {
            transactionMessage.setDead(Boolean.TRUE);
        }
        transactionMessage.addSendTimes();
        transactionMessage.setModifyTime(LocalDateTime.now());
        final int result = transactionMessageDao.update(transactionMessage);
        if (result > 0) {
            amqpTemplate.convertAndSend(transactionMessage.getConsumerQueue(), transactionMessage.getMessageBody());
        }
    }

    /**
     * 根据消息Id标记消息状态为死亡。
     *
     * @param messageId 消息Id。
     */
    @Override
    public boolean setDeadById(String messageId) {
        if (StringUtils.isEmpty(messageId)) {
            throw new NullPointerException(TIP_MESSAGE_ID_NULL);
        }
        TransactionMessage transactionMessage = getByMessageId(messageId);
        transactionMessage.setDead(Boolean.TRUE);
        transactionMessage.setModifyTime(LocalDateTime.now());
        int result = transactionMessageDao.update(transactionMessage);
        return result > 0;
    }

    /**
     * 根据消息Id查找消息。
     *
     * @param messageId 消息Id。
     * @return 消息实体类。
     */
    @Override
    public TransactionMessage getByMessageId(String messageId) {
        Map<String, Object> params = new HashMap<>();
        params.put("messageId", messageId);
        return transactionMessageDao.findByParam(params);
    }

    /**
     * 根据队列名称批量发送死信队列中的消息。
     *
     * @param queueName 消息名称。
     */
    @Override
    public void reSendDeadByQueueName(String queueName) {
        if (StringUtils.isEmpty(batchSize)) {
            batchSize = "50";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("consumerQueue", queueName);
        params.put("isDead", Boolean.TRUE);
        PageDataEntity<TransactionMessage> dataResult = transactionMessageDao.listByPageAndParam(new PageParamEntity(1, Integer.parseInt(batchSize)), params);
        if (dataResult == null || dataResult.getRecordList() == null) {
            return;
        }
        for (TransactionMessage transactionMessage : dataResult.getRecordList()) {
            transactionMessage.setModifyTime(LocalDateTime.now());
            transactionMessage.addSendTimes();
            int result = transactionMessageDao.update(transactionMessage);
            if (result > 0) {
                amqpTemplate.convertAndSend(transactionMessage.getConsumerQueue(), transactionMessage.getMessageBody());
            }
        }
    }

    /**
     * 根据消息Id删除消息。
     *
     * @param messageId 消息Id。
     */
    @Override
    public void deleteByMessageId(String messageId) {
        if (StringUtils.isEmpty(messageId)) {
            throw new NullPointerException(TIP_MESSAGE_ID_NULL);
        }
        transactionMessageDao.deleteById(messageId);
    }

    /**
     * 获得存货的消息Id。
     *
     * @return 消息Id的集合。
     */
    @Override
    public List<String> getAliveMessageId() {
        return transactionMessageDao.getAliveMessageId();
    }

}
