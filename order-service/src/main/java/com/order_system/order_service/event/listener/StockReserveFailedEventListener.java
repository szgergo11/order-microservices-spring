package com.order_system.order_service.event.listener;

import com.order_system.common.messaging.event.StockReserveConfirmedEvent;
import com.order_system.common.messaging.event.StockReserveFailedEvent;
import com.order_system.common.messaging.model.OrderFailureReason;
import com.order_system.order_service.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.order_system.common.constants.KafkaConstants.STOCK_RESERVE_CONFIRMED_TOPIC;
import static com.order_system.common.constants.KafkaConstants.STOCK_RESERVE_FAILED_TOPIC;

@Slf4j
@Component
public class StockReserveFailedEventListener {
    private final OrderService orderService;

    public StockReserveFailedEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = STOCK_RESERVE_FAILED_TOPIC)
    public void onStockReserved(@Payload StockReserveFailedEvent stockReserveFailedEvent) {
        log.info("STOCK_RESERVE_FAILED event received {}", stockReserveFailedEvent);
        orderService.failOrder(stockReserveFailedEvent.getOrderId(), OrderFailureReason.OUT_OF_STOCK);
    }
}
