package studio.istart.construct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dongyan
 */
@Component
public class Construct_Runner implements CommandLineRunner {

    private final Construct_NotifyProducer producer;

    public Construct_Runner(Construct_NotifyProducer producer) {
        this.producer = producer;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("[ConstructRunner]Sending message...");
        List<Construct_MessageSubscriber> subscribers = getUsers();
        for (Construct_MessageSubscriber subscriber : subscribers) {
            subscriber.setMessage(Construct_MessageType.SYSYTEM, Construct_MessageType.SYSYTEM + "-content");
            producer.produce(subscriber);
        }
    }

    public List<Construct_MessageSubscriber> getUsers() {
        List<Construct_MessageSubscriber> subscribers = new ArrayList<>();
        Map<String, List<String>> notifyConfig = new HashMap<>();

        List<String> receiver = new ArrayList<>();
        receiver.add("aaa-phone num-1");
        receiver.add("aaa-phone num-2");
        notifyConfig.put(Construct_NotifyType.SMS, receiver);

        receiver = new ArrayList<>();
        receiver.add("1@a.com");
        receiver.add("2@a.com");
        notifyConfig.put(Construct_NotifyType.EMAIL, receiver);

        receiver = new ArrayList<>();
        receiver.add("UserA");
        notifyConfig.put(Construct_NotifyType.SITE_MESSAGE, receiver);
        subscribers.add(new Construct_MessageSubscriber("UserA", notifyConfig));

        notifyConfig = new HashMap<>();
        receiver = new ArrayList<>();
        receiver.add("bbb-phone num-1");
        notifyConfig.put(Construct_NotifyType.SMS, receiver);

        receiver = new ArrayList<>();
        receiver.add("UserB");
        notifyConfig.put(Construct_NotifyType.SITE_MESSAGE, receiver);

        subscribers.add(new Construct_MessageSubscriber("UserB", notifyConfig));
        return subscribers;
    }

}
