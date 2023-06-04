package org.hechuans.demo.rabbitmq.producer.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : hechuansheng
 * @date : 2023/5/30 17:09
 * @description :
 * @since : version-1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "hechuans.rabbitmq.fanout")
public class RabbitFanoutConfig {

    private String exchangeName;

    private String queueAName;

    private String queueBName;

    /**
     * 扇形交换机
     * @return FanoutExchange
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(exchangeName);
    }

    @Bean
    public Queue queueDemo1() {
        return new Queue(queueAName);
    }

    @Bean
    public Queue queueDemo2() {
        return new Queue(queueBName);
    }

    /**
     * 绑定扇形交换机和队列1
     * @param fanoutExchange 交换机
     * @param queueDemo1 队列
     * @return Binding
     */
    @Bean
    public Binding bindingDemo1(FanoutExchange fanoutExchange, Queue queueDemo1) {
        return BindingBuilder.bind(queueDemo1).to(fanoutExchange);
    }

    /**
     * 绑定扇形交换机和队列2
     * @param fanoutExchange 交换机
     * @param queueDemo2 队列
     * @return Binding
     */
    @Bean
    public Binding bindingDemo2(FanoutExchange fanoutExchange, Queue queueDemo2) {
        return BindingBuilder.bind(queueDemo2).to(fanoutExchange);
    }

}
