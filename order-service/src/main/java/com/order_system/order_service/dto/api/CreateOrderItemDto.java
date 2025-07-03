package com.order_system.order_service.dto.api;

import lombok.Value;

@Value
public class CreateOrderItemDto {
    Integer productId;
    Integer quantity;
    Integer price;
}
