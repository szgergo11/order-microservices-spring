package com.order_system.order_service.event.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order_system.common.messaging.event.OrderCompletedEvent;
import com.order_system.common.messaging.event.OrderCreatedEvent;
import com.order_system.common.messaging.event.OrderFailedEvent;
import com.order_system.common.messaging.event.OrderPendingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.order_system.common.constants.KafkaConstants.*;

@Slf4j
@Service
public class OrderEventProducerServiceImpl implements OrderEventProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventProducerServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        kafkaTemplate.send(
                ORDER_CREATED_TOPIC,
                String.valueOf(orderCreatedEvent.getOrderDetails().getOrderId()),
                orderCreatedEvent);
        log.info("published ORDER_CREATED event {}", orderCreatedEvent);
    }

    @Override
    public void sendOrderPendingEvent(OrderPendingEvent orderPendingEvent) {
        kafkaTemplate.send(
                ORDER_PENDING_TOPIC,
                String.valueOf(orderPendingEvent.getOrderDetails().getOrderId()),
                orderPendingEvent);
        log.info("published ORDER_PENDING event {}", orderPendingEvent);
    }

    @Override
    public void sendOrderCompletedEvent(OrderCompletedEvent orderCompletedEvent) {
        kafkaTemplate.send(
                ORDER_COMPLETED_TOPIC,
                String.valueOf(orderCompletedEvent.getOrderDetails().getOrderId()),
                orderCompletedEvent);
        log.info("published ORDER_COMPLETED event {}", orderCompletedEvent);
    }

    @Override
    public void sendOrderFailedEvent(OrderFailedEvent orderFailedEvent) {
        kafkaTemplate.send(
                ORDER_FAILED_TOPIC,
                String.valueOf(orderFailedEvent.getOrderDetails().getOrderId()),
                orderFailedEvent);
        log.info("published ORDER_FAILED event {}", orderFailedEvent);
    }
}
