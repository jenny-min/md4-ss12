package com.example.md4ss12.controllers;

import com.example.md4ss12.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/revenue")
    public ResponseEntity<?> revenue(@RequestParam String type) {
        return ResponseEntity.ok(reportService.revenue(type));
    }
}
