package com.zen.product_catalog.product;

import com.zen.product_catalog.product.dto.ProductRequest;
import com.zen.product_catalog.product.dto.ProductResponse;
import com.zen.product_catalog.product.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = findProductById(id);

        return toResponse(product);
    }

    public ProductResponse createProduct(ProductRequest request) {

        Product product = new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice()
        );

        Product savedProduct = productRepository.save(product);

        return toResponse(savedProduct);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {

        Product product = findProductById(id);

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        Product updatedProduct = productRepository.save(product);

        return toResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {

        Product product = findProductById(id);

        productRepository.delete(product);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
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