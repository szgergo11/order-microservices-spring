package com.order_system.stock_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "StockReservationItems")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockReservationItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stockReservationItemSeq")
    @SequenceGenerator(name = "stockReservationItemSeq", sequenceName = "stockReservationItemSeq", allocationSize = 1)
    private Integer id;

    @Column
    private Integer productId;

    @Column
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "stockReservationId", referencedColumnName = "id")
    private StockReservationEntity stockReservation;
}
