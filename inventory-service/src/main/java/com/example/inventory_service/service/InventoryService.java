package com.example.inventory_service.service;

import com.example.inventory_service.model.response.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> skuCodes);
}
