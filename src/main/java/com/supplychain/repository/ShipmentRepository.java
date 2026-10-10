package com.supplychain.repository;
import com.supplychain.entity.PurchaseOrder;
import com.supplychain.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    Optional<Shipment> findByShipmentNumber(String shipmentNumber);

    boolean existsByShipmentNumber(String shipmentNumber);

    Optional<Shipment> findByPurchaseOrder(PurchaseOrder purchaseOrder);

    List<Shipment> findByStatus(String status);
}