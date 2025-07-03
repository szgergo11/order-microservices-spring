package com.order_system.stock_service.service;


import com.order_system.common.messaging.model.OrderDetails;

import java.util.List;

public interface StockService {
    void reserveStock(OrderDetails orderDetails);
    void releaseReservedStock(OrderDetails orderDetails);
}
