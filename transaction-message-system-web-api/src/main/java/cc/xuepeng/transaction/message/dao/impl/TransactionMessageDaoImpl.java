package cc.xuepeng.transaction.message.dao.impl;

import cc.xuepeng.transaction.message.core.dao.BaseDaoImpl;
import cc.xuepeng.transaction.message.dao.TransactionMessageDao;
import cc.xuepeng.transaction.message.entity.TransactionMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息对象的数据访问类。
 *
 * @author xuepeng
 */
@Repository("transactionMessageDao")
public class TransactionMessageDaoImpl extends BaseDaoImpl<TransactionMessage> implements TransactionMessageDao {

    private static final String GET_ALIVE_MESSAGEID = "getAliveMessageId";

    /**
     * 获得存货的消息Id。
     *
     * @return 消息Id的集合。
     */
    @Override
    public List<String> getAliveMessageId() {
        String sqlId = super.getStatement(GET_ALIVE_MESSAGEID);
        return super.getSessionTemplate().selectList(sqlId);
    }

}
