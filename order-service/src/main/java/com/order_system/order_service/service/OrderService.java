package com.order_system.order_service.service;

import com.order_system.common.messaging.event.*;
import com.order_system.common.messaging.model.OrderFailureReason;
import com.order_system.order_service.dto.api.CreateOrderDto;
import com.order_system.order_service.entity.OrderStatus;

public interface OrderService {
    void create(CreateOrderDto createOrderDto);
    void markAsPending(Integer orderId);
    void completeOrder(Integer orderId);
    void failOrder(Integer orderId, OrderFailureReason reason);
}
