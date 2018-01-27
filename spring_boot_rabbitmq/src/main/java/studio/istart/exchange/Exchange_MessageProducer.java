package studio.istart.exchange;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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
