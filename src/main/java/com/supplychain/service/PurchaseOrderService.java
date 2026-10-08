package com.supplychain.service;

import com.supplychain.entity.PurchaseOrder;
import com.supplychain.entity.Supplier;
import com.supplychain.entity.Warehouse;
import com.supplychain.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderService(
            PurchaseOrderRepository purchaseOrderRepository) {

        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public Optional<PurchaseOrder> getPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id);
    }

    public Optional<PurchaseOrder> findByPoNumber(String poNumber) {
        return purchaseOrderRepository.findByPoNumber(poNumber);
    }

    public boolean existsByPoNumber(String poNumber) {
        return purchaseOrderRepository.existsByPoNumber(poNumber);
    }

    public List<PurchaseOrder> findBySupplier(Supplier supplier) {
        return purchaseOrderRepository.findBySupplier(supplier);
    }

    public List<PurchaseOrder> findByWarehouse(Warehouse warehouse) {
        return purchaseOrderRepository.findByWarehouse(warehouse);
    }

    public List<PurchaseOrder> findByStatus(String status) {
        return purchaseOrderRepository.findByStatus(status);
    }

    public void deletePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }
}