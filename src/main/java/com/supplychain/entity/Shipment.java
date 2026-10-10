package com.supplychain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
name = "shipments",
uniqueConstraints = {
@UniqueConstraint(
name = "uq_shipment_purchase_order",
columnNames = {"purchase_order_id"}
)
}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(name = "shipment_number", nullable = false, unique = true, length = 50)
private String shipmentNumber;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "purchase_order_id", nullable = false)
private PurchaseOrder purchaseOrder;

@Column(nullable = false, length = 30)
private String status = "CREATED";

@Column(name = "carrier_name", length = 100)
private String carrierName;

@Column(name = "tracking_number", length = 100)
private String trackingNumber;

@Column(name = "shipped_date")
private LocalDate shippedDate;

@Column(name = "expected_delivery_date")
private LocalDate expectedDeliveryDate;

@Column(name = "delivered_date")
private LocalDate deliveredDate;

@Column(name = "shipping_address", length = 500)
private String shippingAddress;

@Column(name = "created_at")
private LocalDateTime createdAt;

@Column(name = "updated_at")
private LocalDateTime updatedAt;

}
