package com.order_system.order_service.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Integer orderId) {
        super("Order not found with ID: " + orderId);
    }
}
