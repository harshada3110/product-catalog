package com.zen.product_catalog.product.dto;

public class ProductResponse {
    
    private final Long id;
    private final String name;
    private final String description;
    private final double price;

    public ProductResponse(Long id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

}
