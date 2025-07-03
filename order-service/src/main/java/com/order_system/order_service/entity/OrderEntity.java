package com.order_system.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "Orders")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column
    Integer userId;

    @Column
    Instant dateCreate;

    @Column
    OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    List<OrderItemEntity> orderItems;
}
