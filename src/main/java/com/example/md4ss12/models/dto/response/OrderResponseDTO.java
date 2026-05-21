package com.example.md4ss12.models.dto.response;

import com.example.md4ss12.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private String customerName;
    private LocalDateTime createdDate;
    private OrderStatus status;
    private BigDecimal totalMoney;
    private List<OrderItemResponseDTO> items;
}
