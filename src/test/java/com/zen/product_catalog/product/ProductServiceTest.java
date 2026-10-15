package com.zen.product_catalog.product;

import com.zen.product_catalog.product.dto.ProductRequest;
import com.zen.product_catalog.product.dto.ProductResponse;
import com.zen.product_catalog.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @Test
    void shouldReturnAllProducts() {

        Product product1 = new Product(
                "Mechanical Keyboard",
                "Wireless mechanical keyboard",
                89.99
        );

        Product product2 = new Product(
                "Gaming Mouse",
                "Lightweight wireless mouse",
                59.99
        );

        when(productRepository.findAll())
                .thenReturn(List.of(product1, product2));

        List<ProductResponse> products = productService.getProducts();

        assertEquals(2, products.size());

        verify(productRepository).findAll();
    }

    @Test
    void shouldReturnProductById() {

        Product product = new Product(
                "Mechanical Keyboard",
                "Wireless mechanical keyboard",
                89.99
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponse result = productService.getProductById(1L);

        assertEquals("Mechanical Keyboard", result.getName());
        assertEquals(89.99, result.getPrice());

        verify(productRepository).findById(1L);
    }

    @Test
    void shouldCreateProduct() {

        ProductRequest request = new ProductRequest(
                "Monitor",
                "27 inch monitor",
                249.99
        );

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse result = productService.createProduct(request);

        assertEquals("Monitor", result.getName());
        assertEquals("27 inch monitor", result.getDescription());
        assertEquals(249.99, result.getPrice());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldUpdateProduct() {

        Product existingProduct = new Product(
                "Old Keyboard",
                "Old description",
                50.00
        );

        ProductRequest request = new ProductRequest(
                "Updated Keyboard",
                "Updated description",
                99.99
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(existingProduct));

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse result =
                productService.updateProduct(1L, request);

        assertEquals("Updated Keyboard", result.getName());
        assertEquals("Updated description", result.getDescription());
        assertEquals(99.99, result.getPrice());

        verify(productRepository).findById(1L);
        verify(productRepository).save(existingProduct);
    }

    @Test
    void shouldDeleteProduct() {

        Product product = new Product(
                "Mechanical Keyboard",
                "Wireless mechanical keyboard",
                89.99
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).findById(1L);
        verify(productRepository).delete(product);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {

        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(999L)
        );

        verify(productRepository).findById(999L);
    }
}