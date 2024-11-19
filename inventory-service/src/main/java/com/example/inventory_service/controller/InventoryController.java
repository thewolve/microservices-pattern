package com.example.inventory_service.controller;

import com.example.inventory_service.model.response.InventoryResponse;
import com.example.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService service;

    @GetMapping
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCodes){
        return service.isInStock(skuCodes);
    }
}
