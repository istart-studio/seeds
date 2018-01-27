package studio.istart.basic;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author dongyan
 */
public class Basic_Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Basic_Receiver receiver;

    public Basic_Runner(Basic_Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("[Basic_Runner]Sending message...");
        rabbitTemplate.convertAndSend(Basic_Config.queueName, "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

}
