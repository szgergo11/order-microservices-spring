package com.order_system.order_service.event.listener;

import com.order_system.common.messaging.event.StockReserveConfirmedEvent;
import com.order_system.order_service.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.order_system.common.constants.KafkaConstants.*;

@Slf4j
@Component
public class StockReserveConfirmedEventListener {
    private final OrderService orderService;

    public StockReserveConfirmedEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = STOCK_RESERVE_CONFIRMED_TOPIC)
    public void onStockReserved(@Payload StockReserveConfirmedEvent stockReserveConfirmedEvent) {
        log.info("STOCK_RESERVE_CONFIRMED event received {}", stockReserveConfirmedEvent);
        orderService.markAsPending(stockReserveConfirmedEvent.getOrderId());
    }
}
