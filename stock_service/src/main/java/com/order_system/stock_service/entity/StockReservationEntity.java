package com.order_system.stock_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "StockReservations")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stockReservationSeq")
    @SequenceGenerator(name = "stockReservationSeq", sequenceName = "stockReservationSeq", allocationSize = 1)
    private Integer id;

    @Column
    Integer orderId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "stockReservationId")
    List<StockReservationItemEntity> items;
}
