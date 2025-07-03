package com.order_system.stock_service.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Stock")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer productId;

    @Column
    private Integer availableQuantity;
}
