package com.order_system.common.messaging.event;

import com.order_system.common.messaging.model.OrderDetails;
import lombok.Value;

@Value
public class OrderPendingEvent {
    OrderDetails orderDetails;
}
