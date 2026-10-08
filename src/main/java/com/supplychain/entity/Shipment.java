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
            name = "uq_shipment_order",
            columnNames = {"order_id"}
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

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