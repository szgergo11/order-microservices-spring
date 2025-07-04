package com.order_system.common.messaging.event;

import com.order_system.common.messaging.model.OrderDetails;
import com.order_system.common.messaging.model.OrderFailureReason;
import lombok.Value;

@Value
public class OrderFailedEvent {
    OrderDetails orderDetails;
    OrderFailureReason failureReason;
}
