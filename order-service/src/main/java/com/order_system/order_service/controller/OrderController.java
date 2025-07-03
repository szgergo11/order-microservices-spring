package com.order_system.order_service.controller;

import com.order_system.order_service.dto.api.CreateOrderDto;
import com.order_system.order_service.event.producer.OrderEventProducerService;
import com.order_system.order_service.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    void createOrder(@RequestBody CreateOrderDto order) {
        orderService.create(order);
    }

}
