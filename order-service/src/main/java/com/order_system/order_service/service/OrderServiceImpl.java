package com.order_system.order_service.service;

import com.order_system.common.messaging.event.*;
import com.order_system.common.messaging.model.OrderFailureReason;
import com.order_system.order_service.dto.api.CreateOrderDto;
import com.order_system.order_service.entity.OrderEntity;
import com.order_system.order_service.entity.OrderItemEntity;
import com.order_system.order_service.entity.OrderStatus;
import com.order_system.order_service.event.producer.OrderEventProducerService;
import com.order_system.order_service.exception.OrderNotFoundException;
import com.order_system.order_service.mapper.OrderEntityMapper;
import com.order_system.order_service.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventProducerService orderEventProducerService;
    private final OrderEntityMapper orderEntityMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderEventProducerService orderEventProducerService, OrderEntityMapper orderEntityMapper) {
        this.orderRepository = orderRepository;
        this.orderEventProducerService = orderEventProducerService;
        this.orderEntityMapper = orderEntityMapper;
    }

    @Override
    @Transactional
    public void create(CreateOrderDto createOrderDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(createOrderDto.getUserId());
        orderEntity.setDateCreate(Instant.now());
        orderEntity.setStatus(OrderStatus.CREATED);
        orderEntity.setOrderItems(
                createOrderDto.getOrderItems().stream()
                        .map(x -> {
                            OrderItemEntity orderItemEntity = new OrderItemEntity();
                            orderItemEntity.setQuantity(x.getQuantity());
                            orderItemEntity.setOrder(orderEntity);
                            orderItemEntity.setProductId(x.getProductId());
                            orderItemEntity.setUnitPrice(x.getPrice());
                            return orderItemEntity;
                        })
                        .toList()
        );
        orderRepository.save(orderEntity);
        log.info("order {} created and saved to database", orderEntity);

        orderEventProducerService.sendOrderCreatedEvent(
                new OrderCreatedEvent(orderEntityMapper.orderEntityToOrderDetails(orderEntity))
        );
    }

    @Override
    @Transactional
    @CacheEvict(value = "orderStatus", key = "#orderId")
    public void markAsPending(Integer orderId) {
        OrderEntity orderEntity = getOrderEntity(orderId);
        orderEntity.setStatus(OrderStatus.PENDING);
        orderRepository.save(orderEntity);
        log.info("order {} status changed to PENDING and saved to database", orderEntity.getId());

        orderEventProducerService.sendOrderPendingEvent(
                new OrderPendingEvent(orderEntityMapper.orderEntityToOrderDetails(orderEntity))
        );
    }

    @Override
    @Transactional
    @CacheEvict(value = "orderStatus", key = "#orderId")
    public void completeOrder(Integer orderId) {
        OrderEntity orderEntity = getOrderEntity(orderId);
        orderEntity.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(orderEntity);
        log.info("order {} status changed to COMPLETED and saved to database", orderEntity.getId());

        orderEventProducerService.sendOrderCompletedEvent(
                new OrderCompletedEvent(orderEntityMapper.orderEntityToOrderDetails(orderEntity))
        );
    }

    @Override
    @Transactional
    @CacheEvict(value = "orderStatus", key = "#orderId")
    public void failOrder(Integer orderId, OrderFailureReason reason) {
        OrderEntity orderEntity = getOrderEntity(orderId);
        orderEntity.setStatus(OrderStatus.FAILED);
        orderRepository.save(orderEntity);
        log.info("order {} status changed to FAILED and saved to database", orderEntity.getId());

        orderEventProducerService.sendOrderFailedEvent(
                new OrderFailedEvent(orderEntityMapper.orderEntityToOrderDetails(orderEntity), reason)
        );
    }

    @Override
    @Cacheable(value = "orderStatus", key = "#orderId")
    public OrderStatus getOrderStatus(Integer orderId) {
        OrderEntity orderEntity = getOrderEntity(orderId);
        return orderEntity.getStatus();
    }

    private OrderEntity getOrderEntity(Integer orderId) {
        Optional<OrderEntity> foundOrder = orderRepository.findById(orderId);
        if(foundOrder.isEmpty()) {
            log.error("order {} not found", orderId);
            throw new OrderNotFoundException(orderId);
        }
        return foundOrder.get();
    }
}
