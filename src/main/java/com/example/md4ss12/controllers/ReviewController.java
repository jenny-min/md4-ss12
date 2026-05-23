package com.example.md4ss12.controllers;

import com.example.md4ss12.models.dto.request.ReviewRequestDTO;
import com.example.md4ss12.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<?> createReview(@RequestBody ReviewRequestDTO review) {
        return ResponseEntity.ok(reviewService.addReview(review));
    }

    @GetMapping("/products/{id}/reviews")
    public ResponseEntity<?> getReviews(@PathVariable Long id){
        return ResponseEntity.ok(reviewService.getReviewsByProduct(id));
    }
}
