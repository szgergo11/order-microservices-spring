package com.order_system.common.messaging.model;

import lombok.Value;

import java.util.List;

@Value
public class OrderDetails {
    Integer orderId;
    Integer customerId;
    List<OrderItem> items;
    Integer totalPrice;

    @Value
    public static class OrderItem {
        Integer orderItemId;
        Integer productId;
        Integer quantity;
        Integer unitPrice;
    }
}
