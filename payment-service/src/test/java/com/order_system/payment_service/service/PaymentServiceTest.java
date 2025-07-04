package com.order_system.payment_service.service;

import com.order_system.common.messaging.event.PaymentConfirmedEvent;
import com.order_system.common.messaging.model.OrderDetails;
import com.order_system.payment_service.entity.PaymentEntity;
import com.order_system.payment_service.event.producer.PaymentEventProducerService;
import com.order_system.payment_service.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentEventProducerService paymentEventProducerService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void processPayment_WithValidOrderDetails_ShouldSavePayment() {
        // Arrange
        OrderDetails orderDetails = new OrderDetails(1, 2, List.of(), 3);
        when(paymentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        paymentService.processPayment(orderDetails);

        // Assert
        verify(paymentRepository).save(any());
    }

    @Test
    void processPayment_WithValidOrderDetails_ShouldSendEvent() {
        // Arrange
        Integer paymentId = 312;
        String paymentToken = "something";

        OrderDetails orderDetails = new OrderDetails(1, 2, List.of(), 3);
        when(paymentRepository.save(any())).thenAnswer(invocation -> {
            PaymentEntity entity = invocation.getArgument(0);
            entity.setId(paymentId);
            entity.setPaymentToken(paymentToken);
            entity.setPaymentSuccessful(true);
            return entity;
        });

        // Act
        paymentService.processPayment(orderDetails);

        // Assert
        verify(paymentRepository).save(any());

        ArgumentCaptor<PaymentConfirmedEvent> eventCaptor = ArgumentCaptor.forClass(PaymentConfirmedEvent.class);
        verify(paymentEventProducerService).sendPaymentConfirmedEvent(eventCaptor.capture());

        PaymentConfirmedEvent capturedEvent = eventCaptor.getValue();
        assertEquals(paymentId, capturedEvent.getPaymentDetails().getPaymentId());
        assertEquals(paymentToken, capturedEvent.getPaymentDetails().getPaymentToken());
    }
}