package org.hechuans.demo.rabbitmq.producer.test;

import lombok.extern.slf4j.Slf4j;
import org.hechuans.demo.rabbitmq.producer.service.ConfirmMessageService;
import org.hechuans.demo.rabbitmq.producer.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author : hechuansheng
 * @date : 2023/5/30 19:20
 * @description :
 * @since : version-1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {

    @Resource
    private MessageService messageService;

    @Resource
    private ConfirmMessageService confirmMessageService;

    @Test
    public void testFanoutExchange() {
        messageService.sendMsg2FanoutExchange("hello world!");
    }

    @Test
    public void testDirectExchange() {
        messageService.sendMsg2DirectExchange("test direct exchange");
    }

    @Test
    public void testTopicExchange() {
        messageService.sendMsg2TopicExchange("test topic exchange");
    }

    @Test
    public void testTTLMessage() {
        messageService.sendTTLMsg("test ttl message");
    }

    @Test
    public void testDlxMessage() {
        messageService.sendMsg2DlxExchange("test dlx message");
    }

    @Test
    public void testDelayMessage() {
        messageService.sendMsg2DelayExchange("test delay message");
    }

    @Test
    public void testConfirmMessage() {
        confirmMessageService.sendMsg2ConfirmExchange("test confirm message");
    }
}
