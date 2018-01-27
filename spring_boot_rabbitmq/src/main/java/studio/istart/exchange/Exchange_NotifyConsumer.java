package studio.istart.exchange;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author dongyan
 */
@Component
public class Exchange_NotifyConsumer {

    public static final String NOTIFY_EXCHANGE = "exchange-notify-exchange";

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Exchange_NotifyType.EMAIL, durable = "true"),
            exchange = @Exchange(type = ExchangeTypes.TOPIC, value = NOTIFY_EXCHANGE, ignoreDeclarationExceptions = "true"),
            key = Exchange_NotifyType.TOPIC_EMAIL)
    )
    public void sendMail(String message) {
        System.out.println("send MailMessage:" + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Exchange_NotifyType.SMS, durable = "true"),
            exchange = @Exchange(type = ExchangeTypes.TOPIC, value = NOTIFY_EXCHANGE, ignoreDeclarationExceptions = "true"),
            key = Exchange_NotifyType.TOPIC_SMS)
    )
    public void sendSms(String message) {
        System.out.println("send SMS:" + message);
    }
}
