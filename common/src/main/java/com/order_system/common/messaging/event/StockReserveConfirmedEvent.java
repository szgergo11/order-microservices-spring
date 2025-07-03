package com.order_system.common.messaging.event;

import lombok.Value;

@Value
public class StockReserveConfirmedEvent {
    Integer orderId;
}
