package com.order_system.order_service.mapper;

import com.order_system.common.messaging.model.OrderDetails;
import com.order_system.order_service.entity.OrderEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderEntityMapperImpl implements OrderEntityMapper {
    @Override
    public OrderDetails orderEntityToOrderDetails(OrderEntity source) {
        var orderItems = source.getOrderItems();
        return new OrderDetails(
                source.getId(),
                source.getUserId(),
                source.getOrderItems().stream()
                        .map(x -> new OrderDetails.OrderItem(
                                x.getId(),
                                x.getProductId(),
                                x.getQuantity(),
                                x.getUnitPrice()
                        ))
                        .toList(),
                orderItems.stream()
                        .map(x -> x.getQuantity() * x.getUnitPrice())
                        .reduce(0, Integer::sum)
        );
    }
}
