package com.example.inventory_service.service.impl;

import com.example.inventory_service.model.entity.Inventory;
import com.example.inventory_service.model.response.InventoryResponse;
import com.example.inventory_service.repo.InventoryRepository;
import com.example.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository repository;

    @Transactional(readOnly=true)
    @Override
    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        return repository.findBySkuCodeIn(skuCodes).stream()
                .map(this::mapToInventoryResponse).toList();
    }

    private InventoryResponse mapToInventoryResponse(Inventory inventory) {
        return new InventoryResponse(inventory.getSkuCode(),inventory.getQuantity()>0);
    }
}
