package com.order_system.order_service.event.listener;

import com.order_system.common.messaging.event.PaymentFailedEvent;
import com.order_system.common.messaging.model.OrderFailureReason;
import com.order_system.order_service.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.order_system.common.messaging.constants.KafkaConstants.PAYMENT_FAILED_TOPIC;

@Slf4j
@Component
public class PaymentFailedEventListener {
    private final OrderService orderService;

    public PaymentFailedEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = PAYMENT_FAILED_TOPIC)
    public void onPaymentConfirmed(@Payload PaymentFailedEvent paymentFailedEvent) {
        log.info("PAYMENT_FAILED event received {}", paymentFailedEvent);
        orderService.failOrder(paymentFailedEvent.getPaymentDetails().getOrderId(), OrderFailureReason.PAYMENT_FAILED);
    }
}
