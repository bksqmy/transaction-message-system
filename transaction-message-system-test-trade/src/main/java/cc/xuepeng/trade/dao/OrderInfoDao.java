package cc.xuepeng.trade.dao;

import cc.xuepeng.trade.entity.OrderInfo;
import cc.xuepeng.transaction.message.core.dao.BaseDao;

import java.util.List;

/**
 * 订单信息的数据访问接口。
 *
 * @author xuepeng
 */
public interface OrderInfoDao extends BaseDao<OrderInfo> {

    List<String> getOrderIdsByMessageIds(String... messageIds);

}
