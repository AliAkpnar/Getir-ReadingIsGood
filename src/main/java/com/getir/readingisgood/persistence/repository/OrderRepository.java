package com.getir.readingisgood.persistence.repository;

import com.getir.readingisgood.persistence.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    Page<OrderEntity> findByCustomerEntityId(Long customerId, Pageable pageable);

    /*@Lock(value = LockModeType.PESSIMISTIC_WRITE)
    OrderEntity save(OrderEntity entity);*/
}