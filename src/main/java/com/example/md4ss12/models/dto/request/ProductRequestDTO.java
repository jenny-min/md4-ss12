package com.example.md4ss12.models.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequestDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private String size;
    private String toppings;
}
