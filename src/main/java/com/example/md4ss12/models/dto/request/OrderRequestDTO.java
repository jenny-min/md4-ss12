package com.example.md4ss12.models.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private List<OrderItemRequestDTO> items;
}
