package com.supplychain.repository;

import com.supplychain.entity.StockMovement;
import com.supplychain.entity.Product;
import com.supplychain.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByProduct(Product product);

    List<StockMovement> findByWarehouse(Warehouse warehouse);

    List<StockMovement> findByProductAndWarehouse(
            Product product,
            Warehouse warehouse
    );
}