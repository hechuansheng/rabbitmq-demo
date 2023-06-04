package org.hechuans.demo.rabbitmq.producer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 也可以使用插件
 * @author : hechuansheng
 * @date : 2023/6/4 9:35
 * @description :
 * @since : version-1.0
 */
@Configuration
@ConfigurationProperties("hechuans.rabbitmq.delay")
public class RabbitDelayConfig {

    @Setter
    @Getter
    private String exchangeName;

    @Setter
    private String queueNormalName;

    @Setter
    private String queueDelayName;

    @Bean
    public DirectExchange delayExchange() {
        return ExchangeBuilder.directExchange(exchangeName).build();
    }

    @Bean
    public Queue delayNormalQueue() {
        return QueueBuilder
                .durable(queueNormalName)
                .ttl(15000)
                .deadLetterExchange(exchangeName)
                .deadLetterRoutingKey("timeout")
                .build();
    }

    @Bean
    public Queue delayDelayName() {
        return QueueBuilder.durable(queueDelayName).build();
    }

    @Bean
    public Binding bindDelayNormalQueue(DirectExchange delayExchange, Queue delayNormalQueue) {
        return BindingBuilder.bind(delayNormalQueue).to(delayExchange).with("order");
    }

    @Bean
    public Binding bindDelayDelayQueue(DirectExchange delayExchange, Queue delayDelayName) {
        return BindingBuilder.bind(delayDelayName).to(delayExchange).with("timeout");
    }

}
