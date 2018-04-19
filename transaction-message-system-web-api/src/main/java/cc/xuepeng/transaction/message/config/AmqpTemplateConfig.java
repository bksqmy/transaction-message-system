package cc.xuepeng.transaction.message.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * AmqpTemplate的配置类。
 *
 * @author xuepeng
 */
@Configuration
public class AmqpTemplateConfig {

    /**
     * 交换机名称。
     */
    private static final String DEFAULT_DIRECT_EXCHANGE = "transaction.message.direct";
    /**
     * 队列名称。
     */
    private static final String ORDER_QUEUE = "order";
    /**
     * 死信队列名称。
     */
    private static final String DEAD_LETTER_QUEUE = "dead-letter";

    @Bean
    public DirectExchange transactionMessageExchange() {
        return new DirectExchange(DEFAULT_DIRECT_EXCHANGE, true, false);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DEAD_LETTER_QUEUE, true, false, false);
    }

    @Bean
    public Queue orderQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", DEFAULT_DIRECT_EXCHANGE);
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE);
        return new Queue(ORDER_QUEUE, true, false, false, args);
    }

    @Bean
    public Binding tradeBinding() {
        return BindingBuilder.bind(orderQueue()).to(transactionMessageExchange()).with(ORDER_QUEUE);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(transactionMessageExchange()).with(DEAD_LETTER_QUEUE);
    }

}
