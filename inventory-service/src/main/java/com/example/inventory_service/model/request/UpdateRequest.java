package com.example.inventory_service.model.request;

public record UpdateRequest(
        String skuCode,
        Integer quantity
) {
}
