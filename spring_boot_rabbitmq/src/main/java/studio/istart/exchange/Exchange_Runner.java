package studio.istart.exchange;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author dongyan
 */
public class Exchange_Runner implements CommandLineRunner {

    private final Exchange_MessageProducer producer;

    public Exchange_Runner(Exchange_MessageProducer producer) {
        this.producer = producer;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("[Exchange_Runner]Sending message...");
        producer.produce(Exchange_MessageType.USER, Exchange_MessageType.USER + "-content");
        producer.produce(Exchange_MessageType.ORDER, Exchange_MessageType.ORDER + "-content");
        producer.produce(Exchange_MessageType.PAYMENT, Exchange_MessageType.PAYMENT + "-content");
    }

}
