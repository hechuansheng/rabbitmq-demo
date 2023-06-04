package org.hechuans.demo.rabbitmq.producer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : hechuansheng
 * @date : 2023/6/2 16:44
 * @description :
 * @since : version-1.0
 */
@Configuration
@ConfigurationProperties("hechuans.rabbitmq.dlx")
public class RabbitDlxConfig {

    @Setter
    @Getter
    private String exchangeNormalName;

    @Setter
    private String queueNormalName;

    @Setter
    private String exchangeDlxName;

    @Setter
    private String queueDlxName;

    @Bean
    public DirectExchange normalExchange() {
        return ExchangeBuilder
                .directExchange(exchangeNormalName)
                //备用交换机
//                .alternate("")
                .build();
    }

    @Bean
    public Queue normalQueue() {
        return QueueBuilder
                .durable(queueNormalName)
                //设置过期时间
                .withArgument("x-message-ttl", 15000)
                //或者设置最大长度
//                .maxLength(100)
                //设置死信交换机
                .withArgument("x-dead-letter-exchange", exchangeDlxName)
                //设置死信交换机路由
                .withArgument("x-dead-letter-routing-key", "error")
                .build();
    }

    @Bean
    public Binding bindingNormalQE(DirectExchange normalExchange, Queue normalQueue) {
        return BindingBuilder.bind(normalQueue).to(normalExchange).with("order");
    }

    @Bean
    public DirectExchange dlxExchange() {
        return ExchangeBuilder.directExchange(exchangeDlxName).build();
    }

    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.durable(queueDlxName).build();
    }

    @Bean
    public Binding bindingDlxQE(DirectExchange dlxExchange, Queue dlxQueue) {
        return BindingBuilder.bind(dlxQueue).to(dlxExchange).with("error");
    }

}
