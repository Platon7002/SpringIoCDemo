package com.example.springdemo.sender;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class EmailSender implements NotificationSender {

    public EmailSender() {
        System.out.println("[контейнер] создан бин EmailSender");
    }

    @Override
    public void send(String recipient, String text) {
        System.out.println("   EMAIL -> " + recipient + ": " + text);
    }

    @Override
    public String channel() {
        return "email";
    }
}
