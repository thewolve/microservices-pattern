package com.example.product_service.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
@Document
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
