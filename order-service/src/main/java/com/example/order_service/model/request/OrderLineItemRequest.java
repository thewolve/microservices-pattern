package com.example.order_service.model.request;

import java.math.BigDecimal;

public record OrderLineItemRequest(
        String skuCode,
        BigDecimal price,
        Integer quantity
) {
}
