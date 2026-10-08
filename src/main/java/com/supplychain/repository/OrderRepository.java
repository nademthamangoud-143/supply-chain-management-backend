package com.supplychain.repository;

import com.supplychain.entity.Order;
import com.supplychain.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    boolean existsByOrderNumber(String orderNumber);

    List<Order> findByWarehouse(Warehouse warehouse);

    List<Order> findByStatus(String status);
}