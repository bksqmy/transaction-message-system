package cc.xuepeng.trade.service;

import cc.xuepeng.trade.entity.OrderInfo;

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

}
