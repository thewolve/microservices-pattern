package com.example.order_service.service;

import com.example.order_service.model.request.OrderRequest;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity<String> createOrder(OrderRequest request);
}
