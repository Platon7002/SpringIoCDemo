package com.example.springdemo.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springdemo.scope.Ticket;

/**
 * Здесь показаны ВСЕ три способа внедрения:
 * 1) через конструктор  — notificationService, ticketProvider (обязательные зависимости, поле final);
 * 2) через сеттер       — auditLogger (необязательная зависимость, можно подменить позже);
 * 3) через поле         — formatter (короче всего, но поле нельзя сделать final и сложно тестировать).
 *
 * Цепочка бинов: OrderService -> NotificationService -> (EmailSender, MessageFormatter).
 */
@Service
public class OrderService {

    private final NotificationService notificationService;   // конструктор
    private final ObjectProvider<Ticket> ticketProvider;     // конструктор

    private AuditLogger auditLogger;                         // сеттер

    @Autowired
    private MessageFormatter formatter;                      // поле

    public OrderService(NotificationService notificationService, ObjectProvider<Ticket> ticketProvider) {
        this.notificationService = notificationService;
        this.ticketProvider = ticketProvider;
        System.out.println("[контейнер] создан бин OrderService (конструктор отработал)");
    }

    @Autowired
    public void setAuditLogger(AuditLogger auditLogger) {
        this.auditLogger = auditLogger;
        System.out.println("[контейнер] OrderService: сеттер setAuditLogger() вызван Spring'ом");
    }

    public void placeOrder(String user, String product) {
        // ObjectProvider.getObject() каждый раз просит у контейнера новый prototype-бин
        Ticket ticket = ticketProvider.getObject();
        auditLogger.log("заказ «" + product + "» от " + user + ", талон №" + ticket.getNumber());
        notificationService.notifyUser(user, "Ваш заказ " + formatter.format(product) + " принят");
    }

    // Нужны демонстрации, чтобы показать, что все три способа сработали
    public boolean isConstructorInjected() {
        return notificationService != null && ticketProvider != null;
    }

    public boolean isSetterInjected() {
        return auditLogger != null;
    }

    public boolean isFieldInjected() {
        return formatter != null;
    }
}
