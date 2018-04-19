package cc.xuepeng.trade.service.impl;

import cc.xuepeng.trade.dao.OrderInfoDao;
import cc.xuepeng.trade.entity.OrderInfo;
import cc.xuepeng.trade.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单信息的服务类。
 *
 * @author xuepeng
 */
@Service("orderInfoService")
public final class OrderInfoServiceImpl implements OrderInfoService {

    /**
     * 订单信息的数据访问接口。
     */
    @Autowired
    private OrderInfoDao orderInfoDao;

    /**
     * 创建订单，该方法创建的是已支付成功的订单。
     *
     * @param orderInfo 订单信息。
     * @return 是否创建成功。
     */
    @Override
    public boolean createOrder(OrderInfo orderInfo) {
        int result = orderInfoDao.insert(orderInfo);
        return result > 0;
    }

    /**
     * 根据多个MessageId查询OrderId。
     *
     * @param messageIds 消息主键的数组。
     * @return OrderId的集合。
     */
    @Override
    public List<String> getOrderIdsByMessageIds(String... messageIds) {
        return orderInfoDao.getOrderIdsByMessageIds(messageIds);
    }

}
