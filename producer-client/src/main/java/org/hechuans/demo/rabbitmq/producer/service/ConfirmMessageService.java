package org.hechuans.demo.rabbitmq.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.hechuans.demo.rabbitmq.producer.config.RabbitConfirmConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author : hechuansheng
 * @date : 2023/6/4 10:13
 * @description :
 * @since : version-1.0
 */
@Slf4j
@Service
public class ConfirmMessageService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RabbitConfirmConfig rabbitConfirmConfig;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    public void sendMsg2ConfirmExchange(String msg) {
        MessageProperties msgProperties = new MessageProperties();
        msgProperties.setCorrelationId("84848484");
        //也可传CorrelationData到convertAndSend方法
//        CorrelationData correlationData = new CorrelationData();
//        correlationData.setId("84848484");
        Message message = MessageBuilder
                .withBody(msg.getBytes(StandardCharsets.UTF_8))
                .andProperties(msgProperties)
                .build();
        rabbitTemplate.convertAndSend(rabbitConfirmConfig.getExchangeName()+"", "order ", message);
        log.info("MessageService.sendMsg2ConfirmExchange =====> message sent completed, message: {}", message);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String reason) {
        log.info("ConfirmMessageService.confirm =====> correlationData.id: {}; ack: {}; reason: {}",
                null == correlationData ? null : correlationData.getId(), ack, reason);
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("ConfirmMessageService.returnedMessage =====> returnedMessage.getReplyText(): {}", returnedMessage.getReplyText());
    }
}
