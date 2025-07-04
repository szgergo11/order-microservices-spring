package com.order_system.stock_service.service.data;

import com.order_system.stock_service.data.udt.StockReservationItemUDT;

import java.util.List;

public interface StockReserveService {
    Boolean reserveStock(Integer orderId, List<StockReservationItemUDT> values);
    Boolean releaseReservedStock(Integer orderId);
    Boolean finalizeReservedStock(Integer orderId);
}
