package com.example.springdemo.service;

import org.springframework.stereotype.Service;

import com.example.springdemo.sender.NotificationSender;

/**
 * Зависимости внедряются через КОНСТРУКТОР.
 * Параметр типа NotificationSender без @Qualifier: из двух реализаций Spring берёт @Primary (EmailSender).
 */
@Service
public class NotificationService {

    private final NotificationSender sender;
    private final MessageFormatter formatter;

    public NotificationService(NotificationSender sender, MessageFormatter formatter) {
        this.sender = sender;
        this.formatter = formatter;
        System.out.println("[контейнер] создан бин NotificationService (внедрён " + sender.channel() + ")");
    }

    public void notifyUser(String user, String text) {
        sender.send(user, formatter.format(text));
    }

    public String usedChannel() {
        return sender.channel();
    }
}
