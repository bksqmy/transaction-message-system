package cc.xuepeng.account.service;

import cc.xuepeng.account.entity.AccountInfo;

/**
 * 账户信息的服务接口。
 *
 * @author xuepeng
 */
public interface AccountInfoService {

    /**
     * 创建账户信息。
     *
     * @param accountInfo 账户信息。
     * @return 是否创建成功。
     */
    boolean createAccountInfo(AccountInfo accountInfo);

}
