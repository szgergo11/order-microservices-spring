package com.order_system.auth_service.repository;

import com.order_system.auth_service.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {
    Optional<RefreshTokenEntity> findByIdAndExpiresAtAfter(UUID id, Instant date);
    boolean existsByIdAndExpiresAtAfter(UUID id, Instant date);
}
