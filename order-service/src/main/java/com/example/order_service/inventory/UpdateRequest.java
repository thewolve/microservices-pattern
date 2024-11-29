package com.example.order_service.inventory;

public record UpdateRequest(
        String skuCode,
        Integer quantity
) {
}
