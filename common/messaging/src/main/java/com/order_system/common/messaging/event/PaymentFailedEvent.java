package com.order_system.common.messaging.event;

import com.order_system.common.messaging.model.PaymentDetails;
import lombok.Value;

@Value
public class PaymentFailedEvent {
    PaymentDetails paymentDetails;
    String failureReason;
}
