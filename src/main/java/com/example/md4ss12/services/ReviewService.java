package com.example.md4ss12.services;

import com.example.md4ss12.models.dto.request.ReviewRequestDTO;
import com.example.md4ss12.models.entity.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(ReviewRequestDTO request);

    List<Review> getReviewsByProduct(Long productId);
}
