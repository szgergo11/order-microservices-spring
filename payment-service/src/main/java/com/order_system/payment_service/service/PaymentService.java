package com.order_system.payment_service.service;

import com.order_system.common.messaging.event.OrderCreatedEvent;
import com.order_system.common.messaging.model.OrderDetails;

public interface PaymentService {
    void processPayment(OrderDetails orderDetails);
}
