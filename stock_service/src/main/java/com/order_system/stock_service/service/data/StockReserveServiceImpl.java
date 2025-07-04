package com.order_system.stock_service.service.data;

import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.order_system.stock_service.data.udt.StockReservationItemUDT;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StockReserveServiceImpl implements StockReserveService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    @Override
    public Boolean reserveStock(Integer orderId, List<StockReservationItemUDT> values) {
        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(
                     "EXEC ReserveStock ?, ?, ?")) {

            SQLServerDataTable tvp = new SQLServerDataTable();
            tvp.addColumnMetadata("ProductId", Types.INTEGER);
            tvp.addColumnMetadata("Quantity", Types.INTEGER);

            for (StockReservationItemUDT item : values) {
                tvp.addRow(item.getProductId(), item.getQuantity());
            }

            SQLServerCallableStatement sqlServerStmt = callableStatement.unwrap(SQLServerCallableStatement.class);
            sqlServerStmt.setInt(1, orderId);

            sqlServerStmt.setStructured(2, "dbo.StockReservationItems", tvp);

            callableStatement.registerOutParameter(3, Types.BIT);

            callableStatement.execute();

            return callableStatement.getBoolean(3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
