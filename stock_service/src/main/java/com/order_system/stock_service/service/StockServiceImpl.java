package com.order_system.stock_service.service;

import com.order_system.common.messaging.event.StockReserveConfirmedEvent;
import com.order_system.common.messaging.event.StockReserveFailedEvent;
import com.order_system.common.messaging.model.OrderDetails;
import com.order_system.stock_service.data.udt.StockReservationItemUDT;
import com.order_system.stock_service.event.producer.StockReserveEventProducerService;
import com.order_system.stock_service.service.data.StockReserveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockServiceImpl implements StockService {
    private final StockReserveService stockReserveService;
    private final StockReserveEventProducerService stockReserveEventProducerService;

    public StockServiceImpl(StockReserveService stockReserveService, StockReserveEventProducerService stockReserveEventProducerService) {
        this.stockReserveService = stockReserveService;
        this.stockReserveEventProducerService = stockReserveEventProducerService;
    }

    @Override
    public void reserveStock(OrderDetails orderDetails) {
        Boolean reserveStockSuccess = stockReserveService.reserveStock(
                orderDetails.getOrderId(),
                orderDetails.getItems().stream()
                        .map(x -> new StockReservationItemUDT(x.getProductId(), x.getQuantity()))
                        .toList()
        );

        log.info("reserve stock for order {}, success: {}", orderDetails, reserveStockSuccess);

        if(reserveStockSuccess) {
            stockReserveEventProducerService.sendStockReserveConfirmedEvent(
                    new StockReserveConfirmedEvent(orderDetails.getOrderId())
            );
        }
        else {
            stockReserveEventProducerService.sendStockReserveFailedEvent(
                    new StockReserveFailedEvent(orderDetails.getOrderId())
            );
        }
    }

    @Override
    public void releaseReservedStock(OrderDetails orderDetails) {
        Boolean releaseStockSuccess = stockReserveService.releaseReservedStock(
                orderDetails.getOrderId()
        );

        log.info("releasing stock (if reserved) for order {}, success: {}", orderDetails, releaseStockSuccess);
    }

    @Override
    public void finalizeReservedStock(OrderDetails orderDetails) {
        Boolean releaseStockSuccess = stockReserveService.releaseReservedStock(
                orderDetails.getOrderId()
        );

        log.info("finalizing reserved stock for order {}, success: {}", orderDetails, releaseStockSuccess);
    }
}
