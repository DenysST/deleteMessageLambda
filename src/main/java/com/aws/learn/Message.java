package com.aws.learn;

public class Message {
    private Long id;
    private String content;
    private String message_id;
    private String channelArn;

    public Message() {
    }

    public Message(Long id, String content, String message_id, String channelArn) {
        this.id = id;
        this.content = content;
        this.message_id = message_id;
        this.channelArn = channelArn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getChannelArn() {
        return channelArn;
    }

    public void setChannelArn(String channelArn) {
        this.channelArn = channelArn;
    }

    @Override
    public String toString() {
        return "Message{"
                + "id=" + id
                + ", content='" + content
                + ", message_id='" + message_id
                + ", channelArn='" + channelArn
                + '}';
    }
}
