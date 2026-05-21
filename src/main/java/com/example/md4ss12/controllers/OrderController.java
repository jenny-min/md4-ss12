package com.example.md4ss12.controllers;

import com.example.md4ss12.enums.OrderStatus;
import com.example.md4ss12.models.dto.request.OrderRequestDTO;
import com.example.md4ss12.models.dto.response.ApiResponse;
import com.example.md4ss12.models.dto.response.OrderResponseDTO;
import com.example.md4ss12.models.entity.Order;
import com.example.md4ss12.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // CUSTOMER đặt hàng
    @PostMapping
    public ApiResponse<OrderResponseDTO> create(
            @RequestBody OrderRequestDTO request
    ) {

        return ApiResponse.<OrderResponseDTO>builder()
                .success(true)
                .message("Đặt hàng thành công")
                .data(orderService.createOrder(request))
                .build();
    }

    // CUSTOMER xem đơn của mình
    @GetMapping("/my")
    public ApiResponse<List<OrderResponseDTO>> myOrders() {

        return ApiResponse.<List<OrderResponseDTO>>builder()
                .success(true)
                .message("Lấy lịch sử đơn hàng thành công")
                .data(orderService.myOrders())
                .build();
    }

    // STAFF ADMIN xem tất cả
    @GetMapping
    public ApiResponse<List<OrderResponseDTO>> getAll() {

        return ApiResponse.<List<OrderResponseDTO>>builder()
                .success(true)
                .message("Lấy tất cả đơn hàng thành công")
                .data(orderService.getAll())
                .build();
    }

    // STAFF update status
    @PutMapping("/{id}/status")
    public ApiResponse<OrderResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ) {

        return ApiResponse.<OrderResponseDTO>builder()
                .success(true)
                .message("Cập nhật trạng thái thành công")
                .data(orderService.updateStatus(id, status))
                .build();
    }
}
