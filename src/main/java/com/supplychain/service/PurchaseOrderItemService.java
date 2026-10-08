package com.supplychain.service;

import com.supplychain.entity.Product;
import com.supplychain.entity.PurchaseOrder;
import com.supplychain.entity.PurchaseOrderItem;
import com.supplychain.repository.PurchaseOrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderItemService {

    private final PurchaseOrderItemRepository purchaseOrderItemRepository;

    public PurchaseOrderItemService(
            PurchaseOrderItemRepository purchaseOrderItemRepository) {

        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
    }

    public PurchaseOrderItem saveItem(PurchaseOrderItem item) {
        return purchaseOrderItemRepository.save(item);
    }

    public List<PurchaseOrderItem> getAllItems() {
        return purchaseOrderItemRepository.findAll();
    }

    public Optional<PurchaseOrderItem> getItemById(Long id) {
        return purchaseOrderItemRepository.findById(id);
    }

    public List<PurchaseOrderItem> findByPurchaseOrder(
            PurchaseOrder purchaseOrder) {

        return purchaseOrderItemRepository.findByPurchaseOrder(
                purchaseOrder
        );
    }

    public Optional<PurchaseOrderItem> findByPurchaseOrderAndProduct(
            PurchaseOrder purchaseOrder,
            Product product) {

        return purchaseOrderItemRepository.findByPurchaseOrderAndProduct(
                purchaseOrder,
                product
        );
    }

    public void deleteItem(Long id) {
        purchaseOrderItemRepository.deleteById(id);
    }
}