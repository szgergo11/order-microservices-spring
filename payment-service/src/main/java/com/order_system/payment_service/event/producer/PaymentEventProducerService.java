package com.order_system.payment_service.event.producer;

import com.order_system.common.messaging.event.PaymentConfirmedEvent;
import com.order_system.common.messaging.event.PaymentFailedEvent;

public interface PaymentEventProducerService {
    void sendPaymentConfirmedEvent(PaymentConfirmedEvent paymentConfirmedEvent);
    void sendPaymentFailedEvent(PaymentFailedEvent paymentFailedEvent);
}
