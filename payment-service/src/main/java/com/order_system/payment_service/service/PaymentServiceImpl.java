package com.order_system.payment_service.service;

import com.order_system.common.messaging.event.OrderCreatedEvent;
import com.order_system.common.messaging.event.PaymentConfirmedEvent;
import com.order_system.common.messaging.model.OrderDetails;
import com.order_system.common.messaging.model.PaymentDetails;
import com.order_system.payment_service.entity.PaymentEntity;
import com.order_system.payment_service.event.producer.PaymentEventProducerService;
import com.order_system.payment_service.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;
    private final PaymentEventProducerService paymentEventProducerService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentEventProducerService paymentEventProducerService) {
        this.paymentRepository = paymentRepository;
        this.paymentEventProducerService = paymentEventProducerService;
    }

    @Override
    public void processPayment(OrderDetails orderDetails) {
        // payment...
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPaymentToken(UUID.randomUUID().toString());
        paymentEntity.setPaymentSuccessful(true);
        paymentEntity.setOrderId(orderDetails.getOrderId());
        paymentEntity.setAmount(orderDetails.getTotalPrice());

        paymentRepository.save(paymentEntity);
        log.info("payment {} created and saved to database", paymentEntity);

        paymentEventProducerService.sendPaymentConfirmedEvent(
                new PaymentConfirmedEvent(new PaymentDetails(
                        paymentEntity.getId(),
                        orderDetails.getOrderId(),
                        orderDetails.getCustomerId(),
                        paymentEntity.getPaymentToken(),
                        orderDetails.getTotalPrice()
                )));
    }
}
