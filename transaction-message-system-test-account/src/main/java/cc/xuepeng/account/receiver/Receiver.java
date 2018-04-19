package cc.xuepeng.account.receiver;

import cc.xuepeng.account.entity.AccountInfo;
import cc.xuepeng.account.service.AccountInfoService;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RabbitListener(queues = "order")
public class Receiver {

    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private RestTemplate restTemplate;

    @RabbitHandler
    public void process(String messageBody) {
        try {
            AccountInfo accountInfo = convertToAccountInfo(messageBody);
            if (accountInfoService.createAccountInfo(accountInfo)) {
                deleleMessage(accountInfo.getOrderId());
            }
        } catch (Exception e) {
             throw new AmqpRejectAndDontRequeueException("");
        }
    }

    private AccountInfo convertToAccountInfo(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(jsonString);
        } catch (JSONException e) {
            throw new ClassCastException("将JSON字符串转换成账户信息失败。");
        }
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setMerchantId(jsonObject.getString("merchantId"));
        accountInfo.setOrderId(jsonObject.getString("orderId"));
        accountInfo.setTotal(jsonObject.getBigDecimal("orderAmount"));
        return accountInfo;
    }

    private void deleleMessage(String messageId) {
        JSONObject param = new JSONObject();
        param.put("messageId", messageId);
        restTemplate.postForEntity("http://localhost:8080/transaction-message/message/delete", param, String.class);
    }

}
