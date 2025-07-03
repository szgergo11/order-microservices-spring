package com.order_system.payment_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Payments")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer orderId;

    @Column
    private String paymentToken;

    @Column
    private Boolean paymentSuccessful;

    @Column
    private Integer amount;
}
