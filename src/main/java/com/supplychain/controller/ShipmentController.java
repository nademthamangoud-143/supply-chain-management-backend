package com.supplychain.controller;

import com.supplychain.entity.PurchaseOrder;
import com.supplychain.entity.Shipment;
import com.supplychain.service.PurchaseOrderService;
import com.supplychain.service.ShipmentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

private final ShipmentService service;
private final PurchaseOrderService purchaseOrderService;

public ShipmentController(
        ShipmentService service,
        PurchaseOrderService purchaseOrderService) {
    this.service = service;
    this.purchaseOrderService = purchaseOrderService;
}

@PostMapping
public ResponseEntity<?> create(@RequestBody Shipment value) {
if (value.getShipmentNumber() == null
        || value.getShipmentNumber().isBlank()) {
    return ResponseEntity.badRequest()
            .body("Shipment number is required.");
}

if (service.existsByShipmentNumber(value.getShipmentNumber())) {
    return ResponseEntity.badRequest()
            .body("Shipment number already exists.");
}

if (value.getPurchaseOrder() == null
        || value.getPurchaseOrder().getId() == null) {
    return ResponseEntity.badRequest()
            .body("A valid Purchase Order ID is required.");
}

Optional<PurchaseOrder> purchaseOrder =
        purchaseOrderService.getPurchaseOrderById(
                value.getPurchaseOrder().getId());

if (purchaseOrder.isEmpty()) {
    return ResponseEntity.badRequest()
            .body("Purchase Order not found.");
}

value.setPurchaseOrder(purchaseOrder.get());

Shipment saved = service.saveShipment(value);

return ResponseEntity.ok(saved);

}


@GetMapping
public ResponseEntity<List<Shipment>> all() {
    return ResponseEntity.ok(service.getAllShipments());
}

@GetMapping("/{id}")
public ResponseEntity<Shipment> get(@PathVariable Long id) {
    return service.getShipmentById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

@GetMapping("/number/{number}")
public ResponseEntity<Shipment> byNumber(
        @PathVariable String number) {

    return service.findByShipmentNumber(number)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

@GetMapping("/status/{status}")
public ResponseEntity<List<Shipment>> byStatus(
        @PathVariable String status) {

    return ResponseEntity.ok(service.findByStatus(status));
}

@GetMapping("/order/{orderId}")
public ResponseEntity<Shipment> byOrder(
        @PathVariable Long orderId) {

    Optional<PurchaseOrder> purchaseOrder =
            purchaseOrderService.getPurchaseOrderById(orderId);

    if (purchaseOrder.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    return service.findByPurchaseOrder(purchaseOrder.get())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

@PutMapping("/{id}")
public ResponseEntity<Shipment> update(
        @PathVariable Long id,
        @RequestBody Shipment value) {

    return service.getShipmentById(id)
            .map(existing -> {
                value.setId(id);
                return ResponseEntity.ok(
                        service.saveShipment(value));
            })
            .orElse(ResponseEntity.notFound().build());
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(
        @PathVariable Long id) {

    if (service.getShipmentById(id).isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    service.deleteShipment(id);
    return ResponseEntity.noContent().build();
}

}
