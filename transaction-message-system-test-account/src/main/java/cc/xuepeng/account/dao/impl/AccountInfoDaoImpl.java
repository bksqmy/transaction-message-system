package cc.xuepeng.account.dao.impl;

import cc.xuepeng.account.dao.AccountInfoDao;
import cc.xuepeng.account.entity.AccountInfo;
import cc.xuepeng.transaction.message.core.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * 账户信息的数据访问类。
 *
 * @author xuepeng
 */
@Repository("accountInfoDao")
public class AccountInfoDaoImpl extends BaseDaoImpl<AccountInfo> implements AccountInfoDao {
}
