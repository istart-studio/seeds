package studio.istart.exchange_example;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dongyan
 */
@Component
public class Exchange_MessageConsumer {

    public static final String MESSAGE_EXCHANGE = "exchange_example-message-exchange_example";

    @Autowired
    private Exchange_NotifyProducer exchangeNotifyProducer;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Exchange_MessageType.USER, durable = "true"),
            exchange = @Exchange(type = ExchangeTypes.DIRECT, value = MESSAGE_EXCHANGE, ignoreDeclarationExceptions = "true"),
            key = Exchange_MessageType.USER)
    )
    public void receiveUserMessage(String message) {
        System.out.println("Message_MessageType.USER:" + message);
        exchangeNotifyProducer.produce(Exchange_MessageType.USER, message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Exchange_MessageType.ORDER, durable = "true"),
            exchange = @Exchange(type = ExchangeTypes.DIRECT, value = MESSAGE_EXCHANGE, ignoreDeclarationExceptions = "true"),
            key = Exchange_MessageType.ORDER)
    )
    public void receiveOrderMessage(String message) {

        System.out.println("Message_MessageType.ORDER:" + message);
        exchangeNotifyProducer.produce(Exchange_MessageType.ORDER, message);

    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Exchange_MessageType.PAYMENT, durable = "true"),
            exchange = @Exchange(type = ExchangeTypes.DIRECT, value = MESSAGE_EXCHANGE, ignoreDeclarationExceptions = "true"),
            key = Exchange_MessageType.PAYMENT)
    )
    public void receivePaymentMessage(String message) {
        System.out.println("Message_MessageType.PAYMENT:" + message);
        exchangeNotifyProducer.produce(Exchange_MessageType.PAYMENT, message);
    }
}
