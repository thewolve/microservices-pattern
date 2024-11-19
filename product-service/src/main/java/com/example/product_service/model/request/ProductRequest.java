package com.example.product_service.model.request;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        String description,
        BigDecimal price) {
}
