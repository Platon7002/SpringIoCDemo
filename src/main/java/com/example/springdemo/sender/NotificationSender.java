package com.example.springdemo.sender;

public interface NotificationSender {

    void send(String recipient, String text);

    String channel();
}
