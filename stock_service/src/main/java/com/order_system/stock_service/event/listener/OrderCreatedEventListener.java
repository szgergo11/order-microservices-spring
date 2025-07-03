package com.order_system.stock_service.event.listener;


import com.order_system.common.messaging.event.OrderCreatedEvent;
import com.order_system.stock_service.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.order_system.common.constants.KafkaConstants.ORDER_CREATED_TOPIC;

@Slf4j
@Component
public class OrderCreatedEventListener {
    private final StockService stockService;

    public OrderCreatedEventListener(StockService stockService) {
        this.stockService = stockService;
    }

    @KafkaListener(topics = ORDER_CREATED_TOPIC)
    public void onOrderCreated(@Payload OrderCreatedEvent orderCreatedEvent) {
        log.info("ORDER_CREATED event received {}", orderCreatedEvent);
        stockService.reserveStock(orderCreatedEvent.getOrderDetails());
    }
}
