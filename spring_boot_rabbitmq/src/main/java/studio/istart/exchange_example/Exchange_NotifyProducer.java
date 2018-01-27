package studio.istart.exchange_example;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 无关消息内容，只管如何处理
 * 消息发送形式，发到哪里等等 由消息的订阅者（message consumer）来决定
 * 具体的执行方式由事件的订阅者决定（notify consumer）来决定
 *
 * @author dongyan
 */
@Component
public class Exchange_NotifyProducer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void produce(String messageType, String messageContent) {
        //find user whose subscribe the message type
        List<Exchange_MessageSubscriber> subscribers = getUsers();
        //generator notify message
        subscribers = subscribers.stream().filter(s -> s.messageType.equals(messageType)).collect(Collectors.toList());
        // topic eg:#.mail.#
        for (Exchange_MessageSubscriber subscriber : subscribers) {
            rabbitTemplate.convertAndSend(Exchange_NotifyConsumer.NOTIFY_EXCHANGE,
                    "TEST" + subscriber.notifyForm, subscriber.userName + ":" + messageContent);
        }
    }

    public List<Exchange_MessageSubscriber> getUsers() {
        List<Exchange_MessageSubscriber> subscribers = new ArrayList<>();
        subscribers.add(new Exchange_MessageSubscriber(Exchange_MessageType.USER, "UserA", ".EMAIL.SMS."));
        subscribers.add(new Exchange_MessageSubscriber(Exchange_MessageType.USER, "UserB", ".EMAIL."));
        subscribers.add(new Exchange_MessageSubscriber(Exchange_MessageType.PAYMENT, "UserB", ".SMS.EMAIL."));
        subscribers.add(new Exchange_MessageSubscriber(Exchange_MessageType.ORDER, "UserA", ".EMAIL.SMS."));
        return subscribers;
    }
}
