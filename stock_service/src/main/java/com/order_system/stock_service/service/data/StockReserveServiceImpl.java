package com.order_system.stock_service.service.data;

import com.order_system.stock_service.data.udt.StockReservationItemUDT;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StockReserveServiceImpl implements StockReserveService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Boolean reserveStock(Integer orderId, List<StockReservationItemUDT> values){
        String param = values.stream()
                .map(x -> "(" + x.getProductId() + "," + x.getQuantity() + ")")
                .collect(Collectors.joining(","));

        Query query = entityManager.createNativeQuery(
                "DECLARE @t StockReservationItems;" +
                "INSERT INTO @t (ProductId, Quantity) VALUES " + param + ";" +
                "DECLARE @result BIT;" +
                "EXEC ReserveStock ?1, @t, @result output;" +
                "SELECT @result;");
        query.setParameter(1, orderId);

        return (Boolean) query.getSingleResult();
    }

    @Override
    public Boolean releaseReservedStock(Integer orderId) {
        Query query = entityManager.createNativeQuery(
                "DECLARE @result BIT;" +
                "EXEC ReleaseReservedStock ?1, @result output;" +
                "SELECT @result;");
        query.setParameter(1, orderId);

        return (Boolean) query.getSingleResult();
    }
}
