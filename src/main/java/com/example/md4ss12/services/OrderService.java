package com.example.md4ss12.services;

import com.example.md4ss12.enums.OrderStatus;
import com.example.md4ss12.models.dto.request.OrderItemRequestDTO;
import com.example.md4ss12.models.dto.request.OrderRequestDTO;
import com.example.md4ss12.models.dto.response.OrderItemResponseDTO;
import com.example.md4ss12.models.dto.response.OrderResponseDTO;
import com.example.md4ss12.models.entity.Order;
import com.example.md4ss12.models.entity.OrderItem;
import com.example.md4ss12.models.entity.Product;
import com.example.md4ss12.models.entity.User;
import com.example.md4ss12.repositories.OrderItemRepository;
import com.example.md4ss12.repositories.OrderRepository;
import com.example.md4ss12.repositories.ProductRepository;
import com.example.md4ss12.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    //tạo đơn hàng
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();

        BigDecimal totalMoney = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemDTO : request.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPriceBuy(product.getPrice());

            orderItems.add(item);
        }

        order.setOrderItems(orderItems);
        order.setTotalMoney(totalMoney);

        Order savedOrder = orderRepository.save(order);

        return convertToDTO(savedOrder);
    }

    //xem lịch sử đơn
    public List<OrderResponseDTO> myOrders(){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        return orderRepository.findByUser(user)
                .stream()
                .toList();
    }

    // staff xem tất cả
    public List<OrderResponseDTO> getAll() {

        return orderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    //cập nhật trạng thái
    public OrderResponseDTO updateStatus(Long id, OrderStatus status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy order"));

        order.setStatus(status);

        Order updatedOrder = orderRepository.save(order);

        return convertToDTO(updatedOrder);
    }

    private OrderResponseDTO convertToDTO(Order order) {

        List<OrderItemResponseDTO> itemDTOs =
                order.getOrderItems()
                        .stream()
                        .map(item -> OrderItemResponseDTO.builder()
                                .productName(item.getProduct().getName())
                                .quantity(item.getQuantity())
                                .priceBuy(item.getPriceBuy())
                                .build())
                        .toList();

        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerName(order.getUser().getEmail())
                .createdDate(order.getCreatedDate())
                .status(order.getStatus())
                .totalMoney(order.getTotalMoney())
                .items(itemDTOs)
                .build();
    }
}
