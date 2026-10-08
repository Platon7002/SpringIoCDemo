package com.example.springdemo.sender;

import org.springframework.stereotype.Component;

@Component
public class SmsSender implements NotificationSender {

    public SmsSender() {
        System.out.println("[контейнер] создан бин SmsSender");
    }

    @Override
    public void send(String recipient, String text) {
        System.out.println("   SMS   -> " + recipient + ": " + text);
    }

    @Override
    public String channel() {
        return "sms";
    }
}
