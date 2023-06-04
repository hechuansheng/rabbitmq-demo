package org.hechuans.demo.rabbitmq.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.hechuans.demo.rabbitmq.producer.config.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author : hechuansheng
 * @date : 2023/5/30 17:22
 * @description :
 * @since : version-1.0
 */
@Slf4j
@Service
public class MessageService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RabbitDirectConfig rabbitDirectConfig;

    @Resource
    private RabbitFanoutConfig rabbitFanoutConfig;

    @Resource
    private RabbitTopicConfig rabbitTopicConfig;

    @Resource
    private RabbitDlxConfig rabbitDlxConfig;

    @Resource
    private RabbitDelayConfig rabbitDelayConfig;

    public void sendMsg2FanoutExchange(String msg) {
        Message message = new Message(msg.getBytes(StandardCharsets.UTF_8));
        rabbitTemplate.convertAndSend(rabbitFanoutConfig.getExchangeName(), "", message);

        log.info("MessageService.sendMsg2FanoutExchange =====> message sent completed, message: {}", message);
    }

    public void sendTTLMsg(String msg) {
        MessageProperties properties = new MessageProperties();
        properties.setExpiration("10000");
        Message message = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8)).andProperties(properties).build();
        rabbitTemplate.convertAndSend(rabbitFanoutConfig.getExchangeName(), "", message);

        log.info("MessageService.sendTTLMsg =====> message sent completed, message: {}", message);
    }


    public void sendMsg2DirectExchange(String msg) {
        Message message = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8)).build();
        rabbitTemplate.convertAndSend(rabbitDirectConfig.getExchangeName(), "error", message);
        rabbitTemplate.convertAndSend(rabbitDirectConfig.getExchangeName(), "info", message);
        rabbitTemplate.convertAndSend(rabbitDirectConfig.getExchangeName(), "warn", message);

        log.info("MessageService.sendMsg2DirectExchange =====> message sent completed, message: {}", message);
    }

    public void sendMsg2TopicExchange(String msg) {
        String fullMessage1 = "route key : app.student.order; " + msg;
        Message message1 = MessageBuilder.withBody(fullMessage1.getBytes(StandardCharsets.UTF_8)).build();
        rabbitTemplate.convertAndSend(rabbitTopicConfig.getExchangeName(), "app.student.order", message1);
        log.info("MessageService.sendMsg2TopicExchange =====> message sent completed, message: {}", fullMessage1);

        String fullMessage2 = "route key : app.teacher.log; " + msg;
        Message message2 = MessageBuilder.withBody(fullMessage2.getBytes(StandardCharsets.UTF_8)).build();
        rabbitTemplate.convertAndSend(rabbitTopicConfig.getExchangeName(), "app.teacher.log", message2);
        log.info("MessageService.sendMsg2TopicExchange =====> message sent completed, message: {}", fullMessage2);

        String fullMessage3 = "route key : web.student.order; " + msg;
        Message message3 = MessageBuilder.withBody(fullMessage3.getBytes(StandardCharsets.UTF_8)).build();
        rabbitTemplate.convertAndSend(rabbitTopicConfig.getExchangeName(), "web.student.order", message3);
        log.info("MessageService.sendMsg2TopicExchange =====> message sent completed, message: {}", fullMessage3);

        String fullMessage4 = "route key : web.teacher.order; " + msg;
        Message message4 = MessageBuilder.withBody(fullMessage4.getBytes(StandardCharsets.UTF_8)).build();
        rabbitTemplate.convertAndSend(rabbitTopicConfig.getExchangeName(), "web.teacher.order", message4);
        log.info("MessageService.sendMsg2TopicExchange =====> message sent completed, message: {}", fullMessage4);

    }

    public void sendMsg2DlxExchange(String msg) {
        Message message = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8)).build();
        rabbitTemplate.convertAndSend(rabbitDlxConfig.getExchangeNormalName(), "order", message);
        log.info("MessageService.sendMsg2DlxExchange =====> message sent completed, message: {}", message);
    }

    public void sendMsg2DelayExchange(String msg) {
        Message message = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8)).build();
        rabbitTemplate.convertAndSend(rabbitDelayConfig.getExchangeName(), "order", message);
        log.info("MessageService.sendMsg2DelayExchange =====> message sent completed, message: {}", message);
    }


}
