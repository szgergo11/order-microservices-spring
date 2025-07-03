package com.order_system.stock_service.event.producer;

import com.order_system.common.messaging.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.order_system.common.constants.KafkaConstants.*;

@Slf4j
@Service
public class StockReserveEventProducerServiceImpl implements StockReserveEventProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public StockReserveEventProducerServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendStockReserveConfirmedEvent(StockReserveConfirmedEvent stockReserveConfirmedEvent) {
        kafkaTemplate.send(
                STOCK_RESERVE_CONFIRMED_TOPIC,
                String.valueOf(stockReserveConfirmedEvent.getOrderId()),
                stockReserveConfirmedEvent);
        log.info("published STOCK_RESERVE_CONFIRMED event {}", stockReserveConfirmedEvent);
    }

    @Override
    public void sendStockReserveFailedEvent(StockReserveFailedEvent stockReserveFailedEvent) {
        kafkaTemplate.send(
                STOCK_RESERVE_FAILED_TOPIC,
                String.valueOf(stockReserveFailedEvent.getOrderId()),
                stockReserveFailedEvent);
        log.info("published STOCK_RESERVE_FAILED event {}", stockReserveFailedEvent);
    }
}
