package com.supplychain.repository;

import com.supplychain.entity.Order;
import com.supplychain.entity.OrderItem;
import com.supplychain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);

    Optional<OrderItem> findByOrderAndProduct(
            Order order,
            Product product
    );
}