package com.order_system.order_service.dto.api;

import lombok.Value;

import java.util.List;

@Value
public class CreateOrderDto {
    Integer userId;
    List<CreateOrderItemDto> orderItems;
}
