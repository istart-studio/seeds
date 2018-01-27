package studio.istart.message_example;

import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;

import java.util.List;
import java.util.Map;

/**
 * @author dongyan
 */
public class Message_MessageSubscriber {
    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 消息内容
     */
    private String messageContent;
    /**
     * 用户
     */
    public String userName;

    public transient Message message;

    /**
     * 通知形式与接收目标
     * email：xx@xx.com;bb@bb.com
     * sms:179xx000;18900-999;
     * site message：userId-1,userId-2
     */
    public Map<String, List<String>> notifyConfig;

    public Message_MessageSubscriber(String userName, Map<String, List<String>> notifyConfig) {
        this.userName = userName;
        this.notifyConfig = notifyConfig;
    }

    /**
     * 序列化
     *
     * @return json字符串
     */
    private String serialization() {
        return new Gson().toJson(this);
    }


    public String getTopicRouteKey() {
        StringBuffer topicRouteKey = new StringBuffer();
        this.notifyConfig.forEach((notifyFrom, receives) -> {
            topicRouteKey.append(notifyFrom + ".");
        });
        return topicRouteKey.toString();
    }

    public Message getMessage() {
        return this.message;
    }


    public void setMessage(String messageType, String content) {
        this.messageType = messageType;
        this.messageContent = content;
        this.message = MessageBuilder.withBody(this.serialization().getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
    }
}

