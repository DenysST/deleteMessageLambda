package com.aws.learn;

public class Message {
    private Long id;
    private String content;

    public Message(String content) {
        this.content = content;
    }

    public Message() {
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

    @Override
    public String toString() {
        return "Message{"
                + "id=" + id
                + ", content='" + content
                + '}';
    }

}
