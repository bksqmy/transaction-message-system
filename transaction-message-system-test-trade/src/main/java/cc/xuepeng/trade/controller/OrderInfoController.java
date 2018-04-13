package cc.xuepeng.trade.controller;

import cc.xuepeng.trade.entity.OrderInfo;
import cc.xuepeng.trade.enums.OrderStatus;
import cc.xuepeng.trade.service.OrderInfoService;
import cc.xuepeng.transaction.message.common.entity.ResultEntity;
import cc.xuepeng.transaction.message.common.enums.ResultStatus;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 订单信息的Controller类。
 *
 * @author xuepeng
 */
@RestController
public class OrderInfoController {

    /**
     * RestTemplate类。
     */
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 订单信息服务接口。
     */
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 支付订单的回调函数（一般是由第三方支付平台发起的回调）。
     *
     * @return 是否支付成功。
     */
    @PostMapping("/order/pay/callback")
    public ResultEntity orderPayCallback() {
        OrderInfo orderInfo = generateOrder();
        // 判断是否预存成功，并且成功的支付了订单。
        if (presave(orderInfo) && orderInfoService.createOrder(orderInfo)) {
            save(orderInfo);
        }
        return new ResultEntity.Builder(ResultStatus.OK).build();
    }


    /**
     * @return 生成一个测试用的订单。
     */
    private OrderInfo generateOrder() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(UUID.randomUUID().toString());
        orderInfo.setOrderId(UUID.randomUUID().toString());
        orderInfo.setOrderStatus(OrderStatus.PAID);
        orderInfo.setOrderAmount(BigDecimal.valueOf(10.00));
        orderInfo.setCustomerId(UUID.randomUUID().toString());
        orderInfo.setMerchantId(UUID.randomUUID().toString());
        orderInfo.setCreateTime(LocalDateTime.now());
        orderInfo.setModifyTime(LocalDateTime.now());
        return orderInfo;
    }

    /**
     * 预存储订单信息。
     *
     * @param orderInfo 订单信息。
     * @return 是否预存储成功。
     */
    private boolean presave(OrderInfo orderInfo) {
        JSONObject param = new JSONObject();
        param.put("messageId", orderInfo.getOrderId());
        param.put("messageBody", JSONObject.toJSONString(orderInfo));
        param.put("consumerQueue", "order");
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:8080/transaction-message/message/presave", param, String.class);
        return (result.getStatusCode() == HttpStatus.OK && JSONObject.parseObject(result.getBody()).getString("status").equals("OK"));
    }

    /**
     * 存储并发送消息。
     *
     * @param orderInfo 订单信息。
     */
    private void save(OrderInfo orderInfo) {
        JSONObject param = new JSONObject();
        param.put("messageId", orderInfo.getOrderId());
        restTemplate.postForEntity("http://localhost:8080/transaction-message/message/save", param, String.class);
    }

}
