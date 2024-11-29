package com.example.order_service.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "inventory-service"
)
public interface InventoryClient {
    @GetMapping("/api/inventory")
    List<InventoryResponse> isInStock(@RequestParam List<String> skuCodes);

    @PutMapping("/api/inventory")
    Boolean isInventoryUpdated(@RequestBody UpdateRequest request);
}
