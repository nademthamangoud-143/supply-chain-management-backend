package com.supplychain.service;

import com.supplychain.entity.PurchaseOrder;
import com.supplychain.entity.Shipment;
import com.supplychain.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public Shipment saveShipment(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Optional<Shipment> getShipmentById(Long id) {
        return shipmentRepository.findById(id);
    }

    public Optional<Shipment> findByShipmentNumber(String shipmentNumber) {
        return shipmentRepository.findByShipmentNumber(shipmentNumber);
    }

    public boolean existsByShipmentNumber(String shipmentNumber) {
        return shipmentRepository.existsByShipmentNumber(shipmentNumber);
    }

    public Optional<Shipment> findByPurchaseOrder(PurchaseOrder purchaseOrder) {
        return shipmentRepository.findByPurchaseOrder(purchaseOrder);
    }

    public List<Shipment> findByStatus(String status) {
        return shipmentRepository.findByStatus(status);
    }

    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }
}