package com.supplychain.repository;

import com.supplychain.entity.Inventory;
import com.supplychain.entity.Product;
import com.supplychain.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductAndWarehouse(
            Product product,
            Warehouse warehouse
    );

    List<Inventory> findByWarehouse(Warehouse warehouse);

    List<Inventory> findByProduct(Product product);
}