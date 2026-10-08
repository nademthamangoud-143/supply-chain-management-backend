package com.supplychain.controller;

import com.supplychain.entity.PurchaseOrder;
import com.supplychain.entity.Supplier;
import com.supplychain.entity.Warehouse;
import com.supplychain.service.PurchaseOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService service;

    public PurchaseOrderController(PurchaseOrderService service) {
        this.service = service;
    }

    // Create purchase order
    @PostMapping
    public ResponseEntity<PurchaseOrder> create(
            @RequestBody PurchaseOrder value) {

        if (value.getPoNumber() != null
                && service.existsByPoNumber(value.getPoNumber())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                service.savePurchaseOrder(value)
        );
    }

    // Get all purchase orders
    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> all() {
        return ResponseEntity.ok(
                service.getAllPurchaseOrders()
        );
    }

    // Get purchase order by ID
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> get(
            @PathVariable Long id) {

        return service.getPurchaseOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get purchase order by PO number
    @GetMapping("/number/{number}")
    public ResponseEntity<PurchaseOrder> byNumber(
            @PathVariable String number) {

        return service.findByPoNumber(number)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get purchase orders by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PurchaseOrder>> byStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                service.findByStatus(status)
        );
    }

    // Get purchase orders by supplier
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<PurchaseOrder>> bySupplier(
            @PathVariable Long supplierId) {

        Supplier supplier = new Supplier();
        supplier.setId(supplierId);

        return ResponseEntity.ok(
                service.findBySupplier(supplier)
        );
    }

    // Get purchase orders by warehouse
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<PurchaseOrder>> byWarehouse(
            @PathVariable Long warehouseId) {

        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);

        return ResponseEntity.ok(
                service.findByWarehouse(warehouse)
        );
    }

    // Update purchase order
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrder> update(
            @PathVariable Long id,
            @RequestBody PurchaseOrder value) {

        return service.getPurchaseOrderById(id)
                .map(existingPurchaseOrder -> {
                    value.setId(id);
                    return ResponseEntity.ok(
                            service.savePurchaseOrder(value)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete purchase order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        if (service.getPurchaseOrderById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deletePurchaseOrder(id);

        return ResponseEntity.noContent().build();
    }
}