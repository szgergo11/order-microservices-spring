package com.order_system.stock_service.event.producer;

import com.order_system.common.messaging.event.*;

public interface StockReserveEventProducerService {
    void sendStockReserveConfirmedEvent(StockReserveConfirmedEvent stockReserveConfirmedEvent);
    void sendStockReserveFailedEvent(StockReserveFailedEvent stockReserveFailedEvent);
}
