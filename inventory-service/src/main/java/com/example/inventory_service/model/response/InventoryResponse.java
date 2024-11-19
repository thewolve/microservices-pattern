package com.example.inventory_service.model.response;

public record InventoryResponse(
        String skuCode,
        Boolean isInStock
) {
}
