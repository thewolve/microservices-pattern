package com.example.product_service.service.impl;

import com.example.product_service.model.entity.Product;
import com.example.product_service.model.request.ProductRequest;
import com.example.product_service.model.response.ProductResponse;
import com.example.product_service.repos.ProductRepository;
import com.example.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public ResponseEntity<String> createProduct(ProductRequest request) {
        Product product = mapProductFrom(request);
        repository.save(product);
        return new ResponseEntity<>("created successfully", HttpStatus.CREATED);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = repository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    private Product mapProductFrom(ProductRequest request) {
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .build();
    }
}
