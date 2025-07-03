package com.order_system.common.messaging.event;

import lombok.Value;

@Value
public class StockReserveFailedEvent {
    Integer orderId;
}
