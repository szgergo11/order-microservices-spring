package com.order_system.stock_service.data.udt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockReservationItemUDT {
    private Integer productId;
    private Integer quantity;
}
