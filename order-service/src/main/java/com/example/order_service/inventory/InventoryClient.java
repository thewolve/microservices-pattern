package com.example.order_service.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "inventory-service",
        url="${application.config.inventory-url}"
)
public interface InventoryClient {
    @GetMapping
    List<InventoryResponse> isInStock(@RequestParam List<String> skuCodes);
}
