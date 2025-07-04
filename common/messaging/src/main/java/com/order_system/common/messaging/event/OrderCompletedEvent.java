package com.order_system.common.messaging.event;

import com.order_system.common.messaging.model.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class OrderCompletedEvent {
    OrderDetails orderDetails;
}
