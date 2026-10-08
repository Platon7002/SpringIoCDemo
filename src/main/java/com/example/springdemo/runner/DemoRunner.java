package com.example.springdemo.runner;

import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.example.springdemo.scope.Ticket;
import com.example.springdemo.sender.NotificationSender;
import com.example.springdemo.service.AlertService;
import com.example.springdemo.service.MessageFormatter;
import com.example.springdemo.service.NotificationService;
import com.example.springdemo.service.OrderService;

@Component
public class DemoRunner implements CommandLineRunner {

    private final ApplicationContext context;
    private final OrderService orderService;
    private final NotificationService notificationService;
    private final AlertService alertService;
    private final Map<String, NotificationSender> allSenders;

    public DemoRunner(ApplicationContext context,
                      OrderService orderService,
                      NotificationService notificationService,
                      AlertService alertService,
                      Map<String, NotificationSender> allSenders) {
        this.context = context;
        this.orderService = orderService;
        this.notificationService = notificationService;
        this.alertService = alertService;
        this.allSenders = allSenders;
    }

    @Override
    public void run(String... args) {
        System.out.println();
        System.out.println("=========== 1. IoC-КОНТЕЙНЕР ===========");
        System.out.println("Контейнер создал и хранит бинов: " + context.getBeanDefinitionCount());
        for (String name : new String[]{"orderService", "notificationService", "alertService",
                "messageFormatter", "auditLogger", "emailSender", "smsSender"}) {
            System.out.println("  бин \"" + name + "\" есть в контейнере: " + context.containsBean(name));
        }

        System.out.println();
        System.out.println("=========== 2. ЦЕПОЧКА ЗАВИСИМОСТЕЙ ===========");
        System.out.println("OrderService -> NotificationService -> (EmailSender + MessageFormatter)");
        orderService.placeOrder("anna@mail.ru", "Ноутбук");

        System.out.println();
        System.out.println("=========== 3. СПОСОБЫ ВНЕДРЕНИЯ (DI) ===========");
        System.out.println("  через конструктор внедрено: " + orderService.isConstructorInjected());
        System.out.println("  через сеттер внедрено:      " + orderService.isSetterInjected());
        System.out.println("  через поле (@Autowired):    " + orderService.isFieldInjected());

        System.out.println();
        System.out.println("=========== 4. SINGLETON и PROTOTYPE ===========");
        MessageFormatter f1 = context.getBean(MessageFormatter.class);
        MessageFormatter f2 = context.getBean(MessageFormatter.class);
        System.out.println("singleton: f1 == f2 ? " + (f1 == f2)
                + "   (хэши: " + System.identityHashCode(f1) + " и " + System.identityHashCode(f2) + ")");

        Ticket t1 = context.getBean(Ticket.class);
        Ticket t2 = context.getBean(Ticket.class);
        System.out.println("prototype: t1 == t2 ? " + (t1 == t2)
                + "   (талоны №" + t1.getNumber() + " и №" + t2.getNumber()
                + ", хэши: " + System.identityHashCode(t1) + " и " + System.identityHashCode(t2) + ")");

        System.out.println("Ещё два заказа: каждый получает новый талон (prototype через ObjectProvider)");
        orderService.placeOrder("boris@mail.ru", "Мышь");
        orderService.placeOrder("clara@mail.ru", "Клавиатура");

        System.out.println();
        System.out.println("=========== 5. @Primary и @Qualifier ===========");
        System.out.println("Реализаций интерфейса NotificationSender: " + allSenders.size() + " -> " + allSenders.keySet());
        System.out.println("NotificationService (без @Qualifier, работает @Primary) использует: "
                + notificationService.usedChannel());
        System.out.println("AlertService (@Qualifier(\"smsSender\")) использует: " + alertService.usedChannel());
        alertService.alert("+7-900-000-00-00", "Пароль изменён");
        System.out.println();
    }
}
