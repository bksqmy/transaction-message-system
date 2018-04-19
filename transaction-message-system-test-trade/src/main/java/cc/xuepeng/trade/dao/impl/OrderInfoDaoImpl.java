package cc.xuepeng.trade.dao.impl;

import cc.xuepeng.trade.dao.OrderInfoDao;
import cc.xuepeng.trade.entity.OrderInfo;
import cc.xuepeng.transaction.message.core.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单信息的数据访问类。
 *
 * @author xuepeng
 */
@Repository("orderInfoDao")
public class OrderInfoDaoImpl extends BaseDaoImpl<OrderInfo> implements OrderInfoDao {

    @Override
    public List<String> getOrderIdsByMessageIds(String... messageIds) {
        return super.getSessionTemplate().selectList(super.getStatement("getOrderIdsByMessageIds"), messageIds);
    }

}
