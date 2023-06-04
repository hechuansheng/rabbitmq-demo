package org.hechuans.demo.rabbitmq.producer.config;

import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : hechuansheng
 * @date : 2023/5/30 19:34
 * @description :
 * @since : version-1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "hechuans.rabbitmq.direct")
public class RabbitDirectConfig {

    private String exchangeName;

    private String queueAName;

    private String queueBName;

    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchangeName).build();
    }

    @Bean
    public Queue queueA() {
        return QueueBuilder.durable(queueAName).build();
    }

    @Bean
    public Queue queueB() {
        return QueueBuilder.durable(queueBName).build();
    }

    /**
     * 绑定直连交换机和队列（指定路由key）
     * @param directExchange 直连交换机
     * @param queueA 队列
     * @return Binding
     */
    @Bean
    public Binding bindingAWithError(DirectExchange directExchange, Queue queueA) {
        return BindingBuilder.bind(queueA).to(directExchange).with("error");
    }

    @Bean
    public Binding bindingBWithError(DirectExchange directExchange, Queue queueB) {
        return BindingBuilder.bind(queueB).to(directExchange).with("error");
    }

    @Bean
    public Binding bindingBWithInfo(DirectExchange directExchange, Queue queueB) {
        return BindingBuilder.bind(queueB).to(directExchange).with("info");
    }

    @Bean
    public Binding bindingBWithWarn(DirectExchange directExchange, Queue queueB) {
        return BindingBuilder.bind(queueB).to(directExchange).with("warn");
    }
}
