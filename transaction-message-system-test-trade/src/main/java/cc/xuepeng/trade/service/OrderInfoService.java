package cc.xuepeng.trade.service;

import cc.xuepeng.trade.entity.OrderInfo;

import java.util.List;

/**
 * 订单信息的服务接口。
 *
 * @author xuepeng
 */
public interface OrderInfoService {

    /**
     * 创建订单，该方法创建的是已支付成功的订单。
     *
     * @param orderInfo 订单信息。
     * @return 是否创建成功。
     */
    boolean createOrder(OrderInfo orderInfo);

    /**
     * 根据多个MessageId查询OrderId。
     *
     * @param messageIds 消息主键的数组。
     * @return OrderId的集合。
     */
    List<String> getOrderIdsByMessageIds(String... messageIds);

}
