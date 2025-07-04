package com.order_system.stock_service.event.listener;


import com.order_system.common.messaging.event.OrderFailedEvent;
import com.order_system.stock_service.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.order_system.common.messaging.constants.KafkaConstants.ORDER_FAILED_TOPIC;

@Slf4j
@Component
public class OrderFailedEventListener {
    private final StockService stockService;

    public OrderFailedEventListener(StockService stockService) {
        this.stockService = stockService;
    }

    @KafkaListener(topics = ORDER_FAILED_TOPIC)
    public void onOrderCreated(@Payload OrderFailedEvent orderFailedEvent) {
        log.info("ORDER_FAILED event received {}", orderFailedEvent);
        stockService.releaseReservedStock(orderFailedEvent.getOrderDetails());
    }
}
