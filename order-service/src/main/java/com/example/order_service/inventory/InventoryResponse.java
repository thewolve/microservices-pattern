package com.example.order_service.inventory;

public record InventoryResponse(
        String skuCode,
        boolean isInStock
) {
}
