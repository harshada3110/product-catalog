package com.zen.product_catalog.product;
import com.zen.product_catalog.product.dto.ProductRequest;
import com.zen.product_catalog.product.dto.ProductResponse;
import com.zen.product_catalog.product.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();
    private long nextId = 1;

    public ProductService() {
        products.add(
                new Product(
                        nextId++,
                        "Mechanical Keyboard",
                        "Wireless mechanical keyboard",
                        89.99
                )
        );

        products.add(
                new Product(
                        nextId++,
                        "Gaming Mouse",
                        "Lightweight wireless mouse",
                        59.99
                )
        );
    }

    public List<ProductResponse> getProducts() {
        return products.stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = findProductById(id);
        return toResponse(product);
    }

    public ProductResponse createProduct(ProductRequest request) {

        Product product = new Product(
                nextId++,
                request.getName(),
                request.getDescription(),
                request.getPrice()
        );

        products.add(product);

        return toResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {

        Product product = findProductById(id);

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        return toResponse(product);
    }

    public void deleteProduct(Long id) {

        Product product = findProductById(id);

        products.remove(product);
    }

    private Product findProductById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
