package com.example.order_service.service.impl;

import com.example.order_service.event.OrderCreatedEvent;
import com.example.order_service.inventory.InventoryClient;
import com.example.order_service.inventory.InventoryResponse;
import com.example.order_service.inventory.UpdateRequest;
import com.example.order_service.model.entity.Order;
import com.example.order_service.model.entity.OrderLineItem;
import com.example.order_service.model.request.OrderLineItemRequest;
import com.example.order_service.model.request.OrderRequest;
import com.example.order_service.repo.OrderRepository;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @Override
    public ResponseEntity<String> createOrder(OrderRequest request) {
        List<InventoryResponse> inventoryResponses = checkProductAvailability(request.orderLineItemRequestList());
        boolean isInStock = inventoryResponses.stream().allMatch(InventoryResponse::isInStock);
        if (!isInStock) throw new RuntimeException("Some items in cart are not in stock");
        List<OrderLineItem> orderLineItemList = request.orderLineItemRequestList().stream()
                .map(this::mapToOrderLineItem)
                .toList();
        Order order = Order.builder()
                .orderNo(getOrderNo())
                .orderLineItems(orderLineItemList)
                .build();
        boolean isInventoryUpdated = updateInventory(order,inventoryResponses);
        if (isInventoryUpdated){
            repository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderCreatedEvent(order.getOrderNo()));
        }

        return new ResponseEntity<>("order created successfully", HttpStatus.CREATED);
    }

    private boolean updateInventory(Order order, List<InventoryResponse> inventoryResponses) {
         return order.getOrderLineItems().stream()
                 .map(x->{
                     int quantity = inventoryResponses.stream().filter(y->y.skuCode().equals(x.getSkuCode())).findFirst().get().quantity()-x.getQuantity();
                     return inventoryClient.isInventoryUpdated(new UpdateRequest(x.getSkuCode(),quantity));
                 }).allMatch(x->x==Boolean.TRUE);
    }

    private List<InventoryResponse> checkProductAvailability(List<OrderLineItemRequest> orderLineItemRequests) {
        List<String> skuCodes = orderLineItemRequests.stream()
                .map(OrderLineItemRequest::skuCode).toList();
        return inventoryClient.isInStock(skuCodes);
    }

    private String getOrderNo() {
        return UUID.randomUUID().toString();
    }

    private OrderLineItem mapToOrderLineItem(OrderLineItemRequest orderLineItemRequest) {
        return OrderLineItem.builder()
                .skuCode(orderLineItemRequest.skuCode())
                .price(orderLineItemRequest.price())
                .quantity(orderLineItemRequest.quantity())
                .build();
    }
}
