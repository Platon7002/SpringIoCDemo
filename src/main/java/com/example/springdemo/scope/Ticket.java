package com.example.springdemo.scope;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Ticket {

    private static final AtomicInteger COUNTER = new AtomicInteger();

    private final int number = COUNTER.incrementAndGet();

    public Ticket() {
        System.out.println("[контейнер] создан НОВЫЙ Ticket №" + number);
    }

    public int getNumber() {
        return number;
    }
}
