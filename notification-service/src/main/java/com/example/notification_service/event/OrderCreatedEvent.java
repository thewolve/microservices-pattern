package com.example.notification_service.event;

import lombok.Data;

@Data
public class OrderCreatedEvent {
    private String orderNo;
}
