package cc.xuepeng.account.service.impl;

import cc.xuepeng.account.dao.AccountInfoDao;
import cc.xuepeng.account.entity.AccountInfo;
import cc.xuepeng.account.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 账户信息的服务接口。
 *
 * @author xuepeng
 */
@Service("accountInfoService")
public class AccountInfoServiceImpl implements AccountInfoService {

    /**
     * 账户信息的数据访问接口。
     */
    @Autowired
    private AccountInfoDao accountInfoDao;

    /**
     * 创建账户信息。
     *
     * @param accountInfo 账户信息。
     * @return 是否创建成功。
     */
    @Override
    public boolean createAccountInfo(AccountInfo accountInfo) {
        if (!isExistsByOrderId(accountInfo.getOrderId())) {
            accountInfo.setId(UUID.randomUUID().toString());
            accountInfo.setTaxes(accountInfo.getTotal().multiply(BigDecimal.valueOf(0.006D)));
            accountInfo.setBalance(accountInfo.getTotal().subtract(accountInfo.getTaxes()));
            accountInfo.setCreateTime(LocalDateTime.now());
            accountInfo.setModifyTime(LocalDateTime.now());
            return accountInfoDao.insert(accountInfo) > 0;
        }
        return true;
    }

    private boolean isExistsByOrderId(final String orderId) {
        Map<String, Object> param = new HashMap<>();
        param.put("orderId", orderId);
        return accountInfoDao.countByParam(param) > 0;
    }

}
