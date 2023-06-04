package org.hechuans.demo.rabbitmq.producer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : hechuansheng
 * @date : 2023/6/4 10:01
 * @description :
 * @since : version-1.0
 */
@Configuration
@ConfigurationProperties("hechuans.rabbitmq.confirm")
public class RabbitConfirmConfig {

    @Setter
    @Getter
    private String exchangeName;

    @Setter
    private String exchangeAlternateName;

    @Setter
    private String queueName;

    @Setter
    private String queueAlternateName;

    @Bean
    public DirectExchange confirmExchange() {
        return ExchangeBuilder
                .directExchange(exchangeName)
                //备用交换机,消息到备用交换机后还是使用原始路由，消息到备用交换机后return模式不会回调
                .alternate(exchangeAlternateName)
                .build();
    }

    @Bean
    public FanoutExchange alternateExchange() {
        return ExchangeBuilder
                .fanoutExchange(exchangeAlternateName)
                //内部交换机
                .internal()
                .build();
    }

    @Bean
    public Queue confirmQueue() {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Queue alternateQueue() {
        return QueueBuilder.durable(queueAlternateName).build();
    }

    @Bean Binding confirmBinding(DirectExchange confirmExchange, Queue confirmQueue) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with("order");
    }

    @Bean
    public Binding alternateBinding(FanoutExchange alternateExchange, Queue alternateQueue) {
        return BindingBuilder
                .bind(alternateQueue)
                .to(alternateExchange);
    }
}
