package studio.istart.exchange_example;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author dongyan
 */
@Component
public class Exchange_MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public Exchange_MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produce(String messageType, String messageContent) {

        //DirectExchanges:messageType->routeKey->queueName
        String routeKey = messageType;
        rabbitTemplate.convertAndSend(routeKey, messageContent);
    }

}
