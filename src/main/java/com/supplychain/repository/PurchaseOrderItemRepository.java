package com.supplychain.repository;

import com.supplychain.entity.PurchaseOrder;
import com.supplychain.entity.PurchaseOrderItem;
import com.supplychain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderItemRepository
        extends JpaRepository<PurchaseOrderItem, Long> {

    List<PurchaseOrderItem> findByPurchaseOrder(PurchaseOrder purchaseOrder);

    Optional<PurchaseOrderItem> findByPurchaseOrderAndProduct(
            PurchaseOrder purchaseOrder,
            Product product
    );
}