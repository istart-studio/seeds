package studio.istart.message_example;

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
public class Message_Runner implements CommandLineRunner {

    private final Message_NotifyProducer producer;

    public Message_Runner(Message_NotifyProducer producer) {
        this.producer = producer;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("[Message_Runner]Sending message...");
        List<Message_MessageSubscriber> subscribers = getUsers();
        for (Message_MessageSubscriber subscriber : subscribers) {
            subscriber.setMessage(Message_MessageType.SYSYTEM, Message_MessageType.SYSYTEM + "-content");
            producer.produce(subscriber);
        }
    }

    public List<Message_MessageSubscriber> getUsers() {
        List<Message_MessageSubscriber> subscribers = new ArrayList<>();
        Map<String, List<String>> notifyConfig = new HashMap<>();

        List<String> receiver = new ArrayList<>();
        receiver.add("aaa-phone num-1");
        receiver.add("aaa-phone num-2");
        notifyConfig.put(Message_NotifyType.SMS, receiver);

        receiver = new ArrayList<>();
        receiver.add("1@a.com");
        receiver.add("2@a.com");
        notifyConfig.put(Message_NotifyType.EMAIL, receiver);

        receiver = new ArrayList<>();
        receiver.add("UserA");
        notifyConfig.put(Message_NotifyType.SITE_MESSAGE, receiver);
        subscribers.add(new Message_MessageSubscriber("UserA", notifyConfig));

        notifyConfig = new HashMap<>();
        receiver = new ArrayList<>();
        receiver.add("bbb-phone num-1");
        notifyConfig.put(Message_NotifyType.SMS, receiver);

        receiver = new ArrayList<>();
        receiver.add("UserB");
        notifyConfig.put(Message_NotifyType.SITE_MESSAGE, receiver);

        subscribers.add(new Message_MessageSubscriber("UserB", notifyConfig));
        return subscribers;
    }

}
