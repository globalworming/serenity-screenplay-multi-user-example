package com.example.websocketdemo.model;

import java.util.Objects;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;

    public static ChatMessage join(String name) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(name);
        chatMessage.setType(MessageType.JOIN);
        return chatMessage;
    }

    public static ChatMessage chat(String name, String content) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(MessageType.CHAT);
        chatMessage.setSender(name);
        chatMessage.setContent(content);
        return chatMessage;
    }

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return type == that.type &&
            Objects.equals(content, that.content) &&
            Objects.equals(sender, that.sender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, content, sender);
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
            "type=" + type +
            ", content='" + content + '\'' +
            ", sender='" + sender + '\'' +
            '}';
    }
}
