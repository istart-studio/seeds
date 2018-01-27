package studio.istart.construct;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author dongyan
 */
@Component
public class Construct_NotifyConsumer {

    public static final String NOTIFY_EXCHANGE = "Construct-notify-exchange";

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Construct_NotifyType.EMAIL, durable = "true"),
            exchange = @Exchange(type = ExchangeTypes.TOPIC, value = NOTIFY_EXCHANGE, ignoreDeclarationExceptions = "true"),
            key = Construct_NotifyType.TOPIC_EMAIL)
    )
    public void sendMail(Message message) {

        System.out.println("[EMAIL]receive :" + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Construct_NotifyType.SMS, durable = "true"),
            exchange = @Exchange(type = ExchangeTypes.TOPIC, value = NOTIFY_EXCHANGE, ignoreDeclarationExceptions = "true"),
            key = Construct_NotifyType.TOPIC_SMS)
    )
    public void sendSms(Message message) {
        System.out.println("[SMS]receive :" + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Construct_NotifyType.TOPIC_SITE_MESSAGE, durable = "true"),
            exchange = @Exchange(type = ExchangeTypes.TOPIC, value = NOTIFY_EXCHANGE, ignoreDeclarationExceptions = "true"),
            key = Construct_NotifyType.TOPIC_SITE_MESSAGE)
    )
    public void sendSiteMessage(Message message) {
        System.out.println("[Site Message]receive :" + message);
    }
}
