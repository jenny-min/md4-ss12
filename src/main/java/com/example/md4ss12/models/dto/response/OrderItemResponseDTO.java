package com.example.md4ss12.models.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDTO {
    private String productName;
    private Integer quantity;
    private BigDecimal priceBuy;
}
