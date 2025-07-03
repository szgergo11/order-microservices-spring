package com.order_system.payment_service.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order_system.common.messaging.event.PaymentConfirmedEvent;
import com.order_system.common.messaging.event.PaymentFailedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.order_system.common.constants.KafkaConstants.*;

@Slf4j
@Service
public class PaymentEventProducerServiceImpl implements PaymentEventProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentEventProducerServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendPaymentConfirmedEvent(PaymentConfirmedEvent paymentConfirmedEvent) {
        kafkaTemplate.send(
                PAYMENT_CONFIRMED_TOPIC,
                String.valueOf(paymentConfirmedEvent.getPaymentDetails().getOrderId()),
                paymentConfirmedEvent);
        log.info("published PAYMENT_CONFIRMED event {}", paymentConfirmedEvent);
    }

    @Override
    public void sendPaymentFailedEvent(PaymentFailedEvent paymentFailedEvent) {
        kafkaTemplate.send(
                PAYMENT_FAILED_TOPIC,
                String.valueOf(paymentFailedEvent.getPaymentDetails().getOrderId()),
                paymentFailedEvent);
        log.info("published PAYMENT_FAILED event {}", paymentFailedEvent);
    }
}
