package com.example.springdemo.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.springdemo.sender.NotificationSender;

/**
 * @Qualifier("smsSender") явно выбирает реализацию по имени бина
 * и перебивает @Primary (который указывает на EmailSender).
 */
@Service
public class AlertService {

    private final NotificationSender sender;

    public AlertService(@Qualifier("smsSender") NotificationSender sender) {
        this.sender = sender;
        System.out.println("[контейнер] создан бин AlertService (внедрён " + sender.channel() + ")");
    }

    public void alert(String user, String text) {
        sender.send(user, "ВНИМАНИЕ! " + text);
    }

    public String usedChannel() {
        return sender.channel();
    }
}
