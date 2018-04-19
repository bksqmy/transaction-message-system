package cc.xuepeng.transaction.message.dao;

import cc.xuepeng.transaction.message.core.dao.BaseDao;
import cc.xuepeng.transaction.message.entity.TransactionMessage;

import java.util.List;

/**
 * 消息对象的数据访问接口。
 *
 * @author xuepeng
 */
public interface TransactionMessageDao extends BaseDao<TransactionMessage> {

    /**
     * 获得存货的消息Id。
     *
     * @return 消息Id的集合。
     */
    List<String> getAliveMessageId();

}
