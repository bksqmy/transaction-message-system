package cc.xuepeng.transaction.message.monitor;

import cc.xuepeng.transaction.message.service.TransactionMessageService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    private TransactionMessageService transactionMessageService;
    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(fixedRate = 60000)
    public void messageHandler() {
        List<String> messageIds = transactionMessageService.getAliveMessageId();
        if (!messageIds.isEmpty()) {
            // 获得订单的处理状态
            ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:8081/order/status", messageIds, String.class);
            if (result.getStatusCode() == HttpStatus.OK) {
                JSONObject resultData = JSONObject.parseObject(result.getBody());
                JSONArray datas = resultData.getJSONArray("data");
                Iterator iterator = datas.iterator();
                while (iterator.hasNext()) {
                    JSONObject message = (JSONObject) iterator.next();
                    String messageId = message.getString("messageId");
                    if ("resend".equals(message.getString("state"))) {
                        transactionMessageService.reSendByMessageId(messageId);
                    } else {
                        transactionMessageService.deleteByMessageId(messageId);
                    }
                }
            }
        }
    }

}
