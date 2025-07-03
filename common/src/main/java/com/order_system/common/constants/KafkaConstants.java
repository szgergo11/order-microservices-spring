package com.order_system.common.constants;

public class KafkaConstants {
    public static final String STOCK_RESERVE_CONFIRMED_TOPIC = "stock.reserve.confirmed";
    public static final String STOCK_RESERVE_FAILED_TOPIC = "stock.reserve.failed";

    public static final String ORDER_CREATED_TOPIC = "order.created";
    public static final String ORDER_PENDING_TOPIC = "order.pending";
    public static final String ORDER_COMPLETED_TOPIC = "order.completed";
    public static final String ORDER_FAILED_TOPIC = "order.failed";

    public static final String PAYMENT_CONFIRMED_TOPIC = "payment.confirmed";
    public static final String PAYMENT_FAILED_TOPIC = "payment.failed";
}
