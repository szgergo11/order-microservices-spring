package com.order_system.common.messaging.model;

import lombok.Value;

@Value
public class PaymentDetails {
    Integer paymentId;
    Integer orderId;
    Integer customerId;
    String paymentToken;
    Integer amount;
}
