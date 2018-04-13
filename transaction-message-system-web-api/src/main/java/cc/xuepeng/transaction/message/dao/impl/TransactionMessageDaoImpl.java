package cc.xuepeng.transaction.message.dao.impl;

import cc.xuepeng.transaction.message.core.dao.BaseDaoImpl;
import cc.xuepeng.transaction.message.dao.TransactionMessageDao;
import cc.xuepeng.transaction.message.entity.TransactionMessage;
import org.springframework.stereotype.Repository;

/**
 * 消息对象的数据访问类。
 *
 * @author xuepeng
 */
@Repository("transactionMessageDao")
public class TransactionMessageDaoImpl extends BaseDaoImpl<TransactionMessage> implements TransactionMessageDao {
}
