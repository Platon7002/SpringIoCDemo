package com.example.springdemo.service;

import org.springframework.stereotype.Component;

/** Простой бин-помощник. Scope не указан, значит singleton (один экземпляр на весь контейнер). */
@Component
public class MessageFormatter {

    public MessageFormatter() {
        System.out.println("[контейнер] создан бин MessageFormatter");
    }

    public String format(String text) {
        return "«" + text + "»";
    }
}
