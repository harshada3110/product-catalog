package com.zen.product_catalog.product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public List<Product> getProducts() {
        return List.of(
            new Product(
                1L,
                "Mechanical Keyboard",
                "Wireless mechanical keyboard",
                89.99
            ),
            new Product(
                2L,
                "Gaming Mouse",
                "Lightweight wireless mouse",
                59.99
            )
        );
    }
}
