package cc.xuepeng.transaction.message.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate的配置类。
 *
 * @author xuepeng
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 连接超时时间。
     */
    @Value("${restTemplate.connectTimeout}")
    private String connectTimeout;
    /**
     * 执行超时时间。
     */
    @Value("${restTemplate.readTimeout}")
    private String readTimeout;

    /**
     * 创建一个RestTemplate对象。
     *
     * @param factory ClientHttpRequestFactory工厂对象。
     * @return RestTemplate对象。
     */
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    /**
     * 创建一个ClientHttpRequestFactory工厂对象。
     *
     * @return ClientHttpRequestFactory工厂对象。
     */
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Integer.parseInt(connectTimeout));//ms
        factory.setReadTimeout(Integer.parseInt(readTimeout));//ms
        return factory;
    }

}
