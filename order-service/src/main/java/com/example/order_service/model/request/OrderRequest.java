package com.example.order_service.model.request;

import java.util.List;

public record OrderRequest(
        List<OrderLineItemRequest> orderLineItemRequestList
) {
}
