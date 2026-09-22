package com.zen.product_catalog.product;

import com.zen.product_catalog.product.dto.ProductRequest;
import com.zen.product_catalog.product.dto.ProductResponse;
import com.zen.product_catalog.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
    }

    @Test
    void shouldReturnAllProducts() {

        List<ProductResponse> products = productService.getProducts();

        assertEquals(2, products.size());
    }

    @Test
    void shouldReturnProductById() {

        ProductResponse product = productService.getProductById(1L);

        assertEquals(1L, product.getId());
        assertEquals("Mechanical Keyboard", product.getName());
    }

    @Test
    void shouldCreateProduct() {

        ProductRequest request = new ProductRequest(
                "Monitor",
                "27 inch monitor",
                249.99
        );

        ProductResponse product = productService.createProduct(request);

        assertNotNull(product.getId());
        assertEquals("Monitor", product.getName());
        assertEquals(249.99, product.getPrice());
    }

    @Test
    void shouldUpdateProduct() {

        ProductRequest request = new ProductRequest(
                "Updated Keyboard",
                "Updated description",
                99.99
        );

        ProductResponse product = productService.updateProduct(1L, request);

        assertEquals("Updated Keyboard", product.getName());
        assertEquals("Updated description", product.getDescription());
        assertEquals(99.99, product.getPrice());
    }

    @Test
    void shouldDeleteProduct() {

        productService.deleteProduct(1L);

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(1L)
        );
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(999L)
        );
    }
}