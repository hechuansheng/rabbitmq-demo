package org.hechuans.demo.rabbitmq.producer.config;

import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : hechuansheng
 * @date : 2023/5/31 11:08
 * @description :
 * @since : version-1.0
 */
@Data
@Configuration
@ConfigurationProperties("hechuans.rabbitmq.topic")
public class RabbitTopicConfig {

    private String exchangeName;

    private String queueAName;

    private String queueBName;

    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(exchangeName).build();
    }

    @Bean
    public Queue topicQueueA() {
        return QueueBuilder.durable(queueAName).build();
    }

    @Bean
    public Queue topicQueueB() {
        return QueueBuilder.durable(queueBName).build();
    }

    @Bean
    public Binding binding2QueueAWithApp(TopicExchange topicExchange, Queue topicQueueA) {
        return BindingBuilder.bind(topicQueueA).to(topicExchange).with("app.#");
    }

    @Bean
    public Binding binding2QueueBWithLog(TopicExchange topicExchange, Queue topicQueueB) {
        return BindingBuilder.bind(topicQueueB).to(topicExchange).with("#.log");
    }

    @Bean
    public Binding binding2QueueBWithTeacher(TopicExchange topicExchange, Queue topicQueueB) {
        return BindingBuilder.bind(topicQueueB).to(topicExchange).with("*.teacher.*");
    }
}
