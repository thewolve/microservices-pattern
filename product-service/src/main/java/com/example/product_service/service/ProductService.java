package com.example.product_service.service;

import com.example.product_service.model.request.ProductRequest;
import com.example.product_service.model.response.ProductResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<String> createProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();
}
