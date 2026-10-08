package com.supplychain.controller;

import com.supplychain.entity.Order;
import com.supplychain.entity.Shipment;
import com.supplychain.service.ShipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {
    private final ShipmentService service;
    public ShipmentController(ShipmentService service){this.service=service;}
    @PostMapping public ResponseEntity<Shipment> create(@RequestBody Shipment value){if(value.getShipmentNumber()!=null&&service.existsByShipmentNumber(value.getShipmentNumber()))return ResponseEntity.badRequest().build();return ResponseEntity.ok(service.saveShipment(value));}
    @GetMapping public ResponseEntity<List<Shipment>> all(){return ResponseEntity.ok(service.getAllShipments());}
    @GetMapping("/{id}") public ResponseEntity<Shipment> get(@PathVariable Long id){return service.getShipmentById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @GetMapping("/number/{number}") public ResponseEntity<Shipment> byNumber(@PathVariable String number){return service.findByShipmentNumber(number).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @GetMapping("/status/{status}") public ResponseEntity<List<Shipment>> byStatus(@PathVariable String status){return ResponseEntity.ok(service.findByStatus(status));}
    @GetMapping("/order/{orderId}") public ResponseEntity<Shipment> byOrder(@PathVariable Long orderId){return service.findByOrder(Order.builder().id(orderId).build()).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @PutMapping("/{id}") public ResponseEntity<Shipment> update(@PathVariable Long id,@RequestBody Shipment value){return service.getShipmentById(id).map(e->{value.setId(id);return ResponseEntity.ok(service.saveShipment(value));}).orElse(ResponseEntity.notFound().build());}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){if(service.getShipmentById(id).isEmpty())return ResponseEntity.notFound().build();service.deleteShipment(id);return ResponseEntity.noContent().build();}
}
