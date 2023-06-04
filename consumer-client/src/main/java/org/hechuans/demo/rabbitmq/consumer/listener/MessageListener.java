package org.hechuans.demo.rabbitmq.consumer.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author : hechuansheng
 * @date : 2023/5/30 19:07
 * @description :
 * @since : version-1.0
 */
@Slf4j
@Component
public class MessageListener {

    @RabbitListener(queues = "queue.fanout.demo1")
    public void listenFanoutDemo1(Message msg) {

        byte[] msgBody = msg.getBody();
        String msgBodyStr = new String(msgBody);
        log.info("MessageListener.listenFanoutDemo1 =====> receive message, msgBodyStr: {}", msgBodyStr);
    }

    @RabbitListener(queues = "queue.fanout.demo2")
    public void listenFanoutDemo2(Message msg) {

        byte[] msgBody = msg.getBody();
        String msgBodyStr = new String(msgBody);
        log.info("MessageListener.listenFanoutDemo2 =====> receive message, msgBodyStr: {}", msgBodyStr);
    }

    @RabbitListener(queues = "queue.direct.demo1")
    public void listenDirectDemo1(Message msg) {

        byte[] msgBody = msg.getBody();
        String msgBodyStr = new String(msgBody);
        log.info("MessageListener.listenDirectDemo1 =====> receive message, msgBodyStr: {}", msgBodyStr);
    }

    @RabbitListener(queues = "queue.direct.demo2")
    public void listenDirectDemo2(Message msg) {

        byte[] msgBody = msg.getBody();
        String msgBodyStr = new String(msgBody);
        log.info("MessageListener.listenDirectDemo2 =====> receive message, msgBodyStr: {}", msgBodyStr);
    }

    @RabbitListener(queues = "queue.topic.demo1")
    public void listenTopicDemo1(Message msg) {

        byte[] msgBody = msg.getBody();
        String msgBodyStr = new String(msgBody);
        log.info("MessageListener.listenTopicDemo1 =====> receive message, msgBodyStr: {}", msgBodyStr);
    }

    @RabbitListener(queues = "queue.topic.demo2")
    public void listenTopicDemo2(Message msg) {

        byte[] msgBody = msg.getBody();
        String msgBodyStr = new String(msgBody);
        log.info("MessageListener.listenTopicDemo2 =====> receive message, msgBodyStr: {}", msgBodyStr);
    }

    /**
     * 手动确认
     * @param msg 消息对象
     * @param channel 信道
     */
    @RabbitListener(queues = "queue.dlx.normal")
    public void listenDlxNormalQueue(Message msg, Channel channel) throws IOException {
        MessageProperties messageProperties = msg.getMessageProperties();
        //获取消息标识
        long deliveryTag = messageProperties.getDeliveryTag();

        String msgBodyStr = null;
        try {
            byte[] msgBody = msg.getBody();
            msgBodyStr = new String(msgBody);
            if (msgBodyStr.length() > 5) {
                throw new RuntimeException("error");
            }
            //参数1：消息标识
            //参数2：批处理标识： 为true时把之前未确认的一起全部确认
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            //参数1：消息标识
            //参数2：批处理标识： 为true时把之前未确认消息（rabbitMQ后台管理Queue中的Unacked状态的消息）一起全部确认
            //参数3：是否重新入队标识
            channel.basicNack(deliveryTag, false, false);

            //拒绝消息
            //参数1：消息标识
            //参数2：是否重新入队标识
//            channel.basicReject(deliveryTag, false);
            e.printStackTrace();
        }

        log.info("MessageListener.listenDlxNormalQueue =====> receive message, deliveryTag: {}, msgBodyStr: {}",
                deliveryTag, msgBodyStr);
    }

    @RabbitListener(queues = "queue.dlx.dlx")
    public void listenDlxDlxQueue(Message msg) {
        byte[] msgBody = msg.getBody();
        String msgBodyStr = new String(msgBody);
        log.info("MessageListener.listenDlxDlxQueue =====> receive message, msgBodyStr: {}", msgBodyStr);
    }

    @RabbitListener(queues = "queue.delay.delay")
    public void listenDelayQueue(Message msg) {
        byte[] msgBody = msg.getBody();
        String msgBodyStr = new String(msgBody);
        log.info("MessageListener.listenDelayQueue =====> receive message, msgBodyStr: {}", msgBodyStr);
    }
}
