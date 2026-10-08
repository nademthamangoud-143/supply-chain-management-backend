package com.supplychain.service;

import com.supplychain.entity.Order;
import com.supplychain.entity.OrderItem;
import com.supplychain.entity.Product;
import com.supplychain.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem saveItem(OrderItem item) {
        return orderItemRepository.save(item);
    }

    public List<OrderItem> getAllItems() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> getItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    public List<OrderItem> findByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    public Optional<OrderItem> findByOrderAndProduct(
            Order order,
            Product product) {

        return orderItemRepository.findByOrderAndProduct(
                order,
                product
        );
    }

    public void deleteItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}