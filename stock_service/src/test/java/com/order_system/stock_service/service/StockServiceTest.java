package com.order_system.stock_service.service;

import com.order_system.common.messaging.event.StockReserveConfirmedEvent;
import com.order_system.common.messaging.event.StockReserveFailedEvent;
import com.order_system.common.messaging.model.OrderDetails;
import com.order_system.stock_service.event.producer.StockReserveEventProducerService;
import com.order_system.stock_service.service.data.StockReserveService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockReserveService stockReserveService;

    @Mock
    private StockReserveEventProducerService stockReserveEventProducerService;

    @InjectMocks
    private StockServiceImpl stockService;

    @Test
    void reserveStock_WhenReservationSucceeds_ShouldSendConfirmedEvent() {
        // Arrange
        OrderDetails orderDetails = new OrderDetails(1, 2, List.of(new OrderDetails.OrderItem(3, 4, 10, 10)), 100);
        when(stockReserveService.reserveStock(any(), any())).thenReturn(true);

        // Act
        stockService.reserveStock(orderDetails);

        // Assert
        verify(stockReserveService).reserveStock(eq(orderDetails.getOrderId()), any());

        ArgumentCaptor<StockReserveConfirmedEvent> eventCaptor =
                ArgumentCaptor.forClass(StockReserveConfirmedEvent.class);
        verify(stockReserveEventProducerService).sendStockReserveConfirmedEvent(eventCaptor.capture());

        StockReserveConfirmedEvent capturedEvent = eventCaptor.getValue();
        assertEquals(orderDetails.getOrderId(), capturedEvent.getOrderId());
    }

    @Test
    void reserveStock_WhenReservationFails_ShouldSendFailedEvent() {
        // Arrange
        OrderDetails orderDetails = new OrderDetails(1, 2, List.of(new OrderDetails.OrderItem(3, 4, 10, 10)), 100);
        when(stockReserveService.reserveStock(eq(orderDetails.getOrderId()), any())).thenReturn(false);

        // Act
        stockService.reserveStock(orderDetails);

        // Assert
        verify(stockReserveService).reserveStock(eq(orderDetails.getOrderId()), anyList());

        ArgumentCaptor<StockReserveFailedEvent> eventCaptor =
                ArgumentCaptor.forClass(StockReserveFailedEvent.class);
        verify(stockReserveEventProducerService).sendStockReserveFailedEvent(eventCaptor.capture());

        StockReserveFailedEvent capturedEvent = eventCaptor.getValue();
        assertEquals(orderDetails.getOrderId(), capturedEvent.getOrderId());
    }

    @Test
    void releaseReservedStock_WithValidOrderDetails_ShouldCallService() {
        // Arrange
        OrderDetails orderDetails = new OrderDetails(1, 2, List.of(new OrderDetails.OrderItem(3, 4, 10, 10)), 100);
        when(stockReserveService.releaseReservedStock(eq(orderDetails.getOrderId()))).thenReturn(true);

        // Act
        stockService.releaseReservedStock(orderDetails);

        // Assert
        verify(stockReserveService).releaseReservedStock(orderDetails.getOrderId());
    }

    @Test
    void finalizeReservedStock_WithValidOrderDetails_ShouldCallService() {
        // Arrange
        OrderDetails orderDetails = new OrderDetails(1, 2, List.of(new OrderDetails.OrderItem(3, 4, 10, 10)), 100);
        when(stockReserveService.finalizeReservedStock(eq(orderDetails.getOrderId()))).thenReturn(true);

        // Act
        stockService.finalizeReservedStock(orderDetails);

        // Assert
        verify(stockReserveService).finalizeReservedStock(orderDetails.getOrderId());
    }
}