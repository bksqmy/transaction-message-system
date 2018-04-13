package cc.xuepeng.transaction.message.controller;

import cc.xuepeng.transaction.message.common.entity.ResultEntity;
import cc.xuepeng.transaction.message.common.enums.ResultStatus;
import cc.xuepeng.transaction.message.entity.TransactionMessage;
import cc.xuepeng.transaction.message.service.TransactionMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 可靠消息的Controller对象，对外暴露WebAPI。
 *
 * @author xuepeng
 */
@RestController
@RequestMapping("/transaction-message")
public class TransactionMessageController {

    /**
     * 可靠消息服务接口。
     */
    @Autowired
    private TransactionMessageService transactionMessageService;

    /**
     * 预存储消息。
     *
     * @param message 消息实体类。
     * @return 操作结果。
     */
    @PostMapping("/message/presave")
    public ResultEntity saveAndWaitingConfirm(@RequestBody TransactionMessage message) {
        if (transactionMessageService.saveAndWaitingConfirm(message)) {
            return new ResultEntity.Builder(ResultStatus.OK).msg("预存储成功。").build();
        } else {
            return new ResultEntity.Builder(ResultStatus.FAIL).msg("预存储失败。").build();
        }
    }

    /**
     * 确认并发送消息。
     *
     * @param message 消息实体类。
     * @return 操作结果。
     */
    @PostMapping("/message/save")
    public ResultEntity confirmAndSendById(@RequestBody TransactionMessage message) {
        transactionMessageService.confirmAndSendById(message.getMessageId());
        return new ResultEntity.Builder(ResultStatus.OK).msg("确认并发送成功。").build();
    }

    /**
     * 存储并发送消息。
     *
     * @param message 消息实体类。
     * @return 操作结果。
     */
    @PostMapping("/message/save-send")
    public ResultEntity saveAndSend(@RequestBody TransactionMessage message) {
        if (transactionMessageService.saveAndSend(message)) {
            return new ResultEntity.Builder(ResultStatus.OK).msg("存储并发送成功。").build();
        } else {
            return new ResultEntity.Builder(ResultStatus.FAIL).msg("存储并发送失败。").build();
        }
    }

    /**
     * 转发消息。
     *
     * @param message 消息实体类。
     * @return 操作结果。
     */
    @PostMapping("/message/direct-send")
    public ResultEntity directAndSend(@RequestBody TransactionMessage message) {
        transactionMessageService.directAndSend(message);
        return new ResultEntity.Builder(ResultStatus.OK).msg("转发成功。").build();
    }

    /**
     * 重发消息。
     *
     * @param message 消息实体类。
     * @return 操作结果。
     */
    @PostMapping("/message/resend")
    public ResultEntity reSendByMessageId(@RequestBody TransactionMessage message) {
        transactionMessageService.reSendByMessageId(message.getMessageId());
        return new ResultEntity.Builder(ResultStatus.OK).msg("根据消息重发成功。").build();
    }

    /**
     * 设置消息为死亡状态。
     *
     * @param message 消息实体类。
     * @return 操作结果。
     */
    @PostMapping("/message/dead")
    public ResultEntity setDeadById(@RequestBody TransactionMessage message) {
        if (transactionMessageService.setDeadById(message.getMessageId())) {
            return new ResultEntity.Builder(ResultStatus.OK).msg("设置消息为死亡状态成功。").build();
        } else {
            return new ResultEntity.Builder(ResultStatus.FAIL).msg("设置消息为死亡状态失败。").build();
        }
    }

    /**
     * 根据消息Id查找消息。
     *
     * @param messageId 消息Id。
     * @return 操作结果。
     */
    @GetMapping("/message/{messageId}")
    public ResultEntity getByMessageId(@PathVariable String messageId) {
        TransactionMessage message = transactionMessageService.getByMessageId(messageId);
        if (message != null) {
            return new ResultEntity.Builder(ResultStatus.OK).data(message).build();
        } else {
            return new ResultEntity.Builder(ResultStatus.FAIL).msg("要查找的消息不存在。").build();
        }
    }

    /**
     * 根据队列名称重发消息。
     *
     * @param message 消息实体类。
     * @return 操作结果。
     */
    @PostMapping("/message/resend-dead")
    public ResultEntity reSendDeadByQueueName(@RequestBody TransactionMessage message) {
        transactionMessageService.reSendDeadByQueueName(message.getConsumerQueue());
        return new ResultEntity.Builder(ResultStatus.OK).msg("根据队列名称重发消息成功。").build();
    }

    /**
     * 根据消息Id删除消息。
     *
     * @param message 消息实体类
     * @return 操作结果。
     */
    @PostMapping("/message/delete")
    public ResultEntity deleteById(@RequestBody TransactionMessage message) {
        transactionMessageService.deleteById(message.getMessageId());
        return new ResultEntity.Builder(ResultStatus.OK).msg("删除消息成功。").build();
    }

}

