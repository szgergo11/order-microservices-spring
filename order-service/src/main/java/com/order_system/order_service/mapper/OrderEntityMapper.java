package com.order_system.order_service.mapper;

import com.order_system.common.messaging.model.OrderDetails;
import com.order_system.order_service.entity.OrderEntity;

public interface OrderEntityMapper {
    OrderDetails orderEntityToOrderDetails(OrderEntity source);
}
