package com.order_system.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "RefreshTokens")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntity {

    @Id
    @Column(name = "Id", columnDefinition = "uniqueidentifier")
    @UuidGenerator
    private UUID id;

    @Column(name = "UserId", insertable = false, updatable = false)
    private Integer userId;

    @Column(name = "CreatedAt")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "ExpiresAt")
    private Instant expiresAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", referencedColumnName = "Id")
    private UserEntity user;
}
