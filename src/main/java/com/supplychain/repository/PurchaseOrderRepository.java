package com.supplychain.repository;

import com.supplychain.entity.PurchaseOrder;
import com.supplychain.entity.Supplier;
import com.supplychain.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    Optional<PurchaseOrder> findByPoNumber(String poNumber);

    boolean existsByPoNumber(String poNumber);

    List<PurchaseOrder> findBySupplier(Supplier supplier);

    List<PurchaseOrder> findByWarehouse(Warehouse warehouse);

    List<PurchaseOrder> findByStatus(String status);
}