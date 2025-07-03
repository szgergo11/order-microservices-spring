package com.order_system.order_service.event.producer;

import com.order_system.common.messaging.event.OrderCompletedEvent;
import com.order_system.common.messaging.event.OrderCreatedEvent;
import com.order_system.common.messaging.event.OrderFailedEvent;
import com.order_system.common.messaging.event.OrderPendingEvent;

public interface OrderEventProducerService {
    void sendOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent);
    void sendOrderPendingEvent(OrderPendingEvent orderPendingEvent);
    void sendOrderCompletedEvent(OrderCompletedEvent orderCompletedEvent);
    void sendOrderFailedEvent(OrderFailedEvent orderFailedEvent);
}
