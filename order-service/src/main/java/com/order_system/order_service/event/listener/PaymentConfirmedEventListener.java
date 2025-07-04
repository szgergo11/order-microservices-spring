package com.order_system.order_service.event.listener;

import com.order_system.common.messaging.event.PaymentConfirmedEvent;
import com.order_system.order_service.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.order_system.common.messaging.constants.KafkaConstants.PAYMENT_CONFIRMED_TOPIC;

@Slf4j
@Component
public class PaymentConfirmedEventListener {
    private final OrderService orderService;

    public PaymentConfirmedEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = PAYMENT_CONFIRMED_TOPIC)
    public void onPaymentConfirmed(@Payload PaymentConfirmedEvent paymentConfirmedEvent) {
        log.info("PAYMENT_CONFIRMED event received {}", paymentConfirmedEvent);
        orderService.completeOrder(paymentConfirmedEvent.getPaymentDetails().getOrderId());
    }
}
