package com.example.springdemo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/** Журнал действий. В OrderService внедряется через СЕТТЕР. */
@Component
public class AuditLogger {

    private final List<String> records = new ArrayList<>();

    public AuditLogger() {
        System.out.println("[контейнер] создан бин AuditLogger");
    }

    public void log(String record) {
        records.add(record);
        System.out.println("   AUDIT: " + record);
    }

    public int count() {
        return records.size();
    }
}
