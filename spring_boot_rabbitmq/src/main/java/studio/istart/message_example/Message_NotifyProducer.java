package studio.istart.message_example;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 无关消息内容，只管如何处理
 * 消息发送形式，发到哪里等等 由消息的订阅者（message consumer）来决定
 * 具体的执行方式由事件的订阅者决定（notify consumer）来决定
 *
 * @author dongyan
 */
@Component
public class Message_NotifyProducer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void produce(Message_MessageSubscriber subscriber) {
        rabbitTemplate.convertAndSend(Message_NotifyConsumer.NOTIFY_EXCHANGE,
                subscriber.getTopicRouteKey(), subscriber.getMessage());
    }


}
