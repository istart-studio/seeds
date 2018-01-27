package studio.istart.exchange_example;

/**
 * @author dongyan
 */
public class Exchange_MessageSubscriber {
    /**
     * 消息类型
     */
    public String messageType;
    /**
     * 用户
     */
    public String userName;
    /**
     * 通知形式
     */
    public String notifyForm;

    /**
     * 接收人
     * email：xx@xx.com;bb@bb.com
     * sms:179xx000;18900-999;
     * site message：userId-1,userId-2
     */
    public String receiver;

    public Exchange_MessageSubscriber(String messageType, String userName, String notifyForm) {
        this.messageType = messageType;
        this.userName = userName;
        this.notifyForm = notifyForm;
    }

}

