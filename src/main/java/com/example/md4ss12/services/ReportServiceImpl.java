package com.example.md4ss12.services;

import com.example.md4ss12.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements  ReportService {
    private final OrderRepository orderRepository;

    @Override
    public Double revenue(String type) {
        LocalDate now = LocalDate.now();

        return switch (type) {
            case "day" ->
                orderRepository.revenueByDate(now);

            case "month" ->
                orderRepository.revenueByMonth(now.getMonthValue());

            case "year" ->
                orderRepository.revenueByYear(now.getYear());

            default ->
                throw new RuntimeException("Type không hợp lệ");
        };
    }
}
