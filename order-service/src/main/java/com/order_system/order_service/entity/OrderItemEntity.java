package com.order_system.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "OrderItems")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private OrderEntity order;

    @Column
    private Integer quantity;

    @Column
    private Integer unitPrice;
}
