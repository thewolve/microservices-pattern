package com.example.order_service.controller;

import com.example.order_service.model.request.OrderRequest;
import com.example.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    @CircuitBreaker(name = "inventory", fallbackMethod = "inventoryFallback")
    @TimeLimiter(name="inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> createOrder(@RequestBody OrderRequest request){
        return CompletableFuture.supplyAsync(()->service.createOrder(request).getBody());
    }

    public CompletableFuture<String> inventoryFallback(OrderRequest request, RuntimeException e){
        return CompletableFuture.supplyAsync(()->"oops! something went wrong, please try again later..");
    }
}
