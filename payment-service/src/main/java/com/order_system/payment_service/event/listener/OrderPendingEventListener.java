package com.order_system.payment_service.event.listener;


import com.order_system.common.messaging.event.OrderCreatedEvent;
import com.order_system.common.messaging.event.OrderPendingEvent;
import com.order_system.payment_service.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.order_system.common.constants.KafkaConstants.ORDER_PENDING_TOPIC;

@Component
public class OrderPendingEventListener {
    private final PaymentService paymentService;

    public OrderPendingEventListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = ORDER_PENDING_TOPIC)
    public void onOrderCreated(@Payload OrderPendingEvent orderPendingEvent) {
        paymentService.processPayment(orderPendingEvent.getOrderDetails());
    }
}
