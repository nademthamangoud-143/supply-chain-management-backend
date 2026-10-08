package com.supplychain.service;

import com.supplychain.entity.Product;
import com.supplychain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create product
    public Product createProduct(Product product) {

        if (product.getCategory() == null ||
                product.getCategory().getId() == null) {

            throw new RuntimeException(
                    "Category is required to create a product"
            );
        }

        if (productRepository.existsBySku(product.getSku())) {

            throw new RuntimeException(
                    "Product with SKU already exists"
            );
        }

        return productRepository.save(product);
    }

    // Get all products
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    // Get product by ID
    public Product getProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with id: " + id
                        )
                );
    }

    // Update product
    public Product updateProduct(
            Long id,
            Product updatedProduct) {

        Product existingProduct =
                getProductById(id);

        if (updatedProduct.getCategory() == null ||
                updatedProduct.getCategory().getId() == null) {

            throw new RuntimeException(
                    "Category is required to update a product"
            );
        }

        existingProduct.setSku(
                updatedProduct.getSku()
        );

        existingProduct.setName(
                updatedProduct.getName()
        );

        existingProduct.setDescription(
                updatedProduct.getDescription()
        );

        existingProduct.setCategory(
                updatedProduct.getCategory()
        );

        existingProduct.setUnitPrice(
                updatedProduct.getUnitPrice()
        );

        existingProduct.setReorderLevel(
                updatedProduct.getReorderLevel()
        );

        existingProduct.setActive(
                updatedProduct.getActive()
        );

        return productRepository.save(existingProduct);
    }

    // Delete product
    public void deleteProduct(Long id) {

        Product product =
                getProductById(id);

        productRepository.delete(product);
    }
}