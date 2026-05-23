package com.example.md4ss12.services;

import com.example.md4ss12.models.dto.request.ReviewRequestDTO;
import com.example.md4ss12.models.entity.Product;
import com.example.md4ss12.models.entity.Review;

import com.example.md4ss12.models.entity.User;
import com.example.md4ss12.repositories.OrderItemRepository;
import com.example.md4ss12.repositories.ProductRepository;
import com.example.md4ss12.repositories.ReviewRepository;
import com.example.md4ss12.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;


    @Override
    public Review addReview(ReviewRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow();

        //Check đã mua hay chưa
        boolean purchased = orderItemRepository.hasPurchased(user.getId(), request.getProductId());
        if (!purchased) {
            throw new RuntimeException("Chưa mua sản phẩm này");
        }

        Product product = productRepository.findById(request.getProductId()).orElseThrow();

        Review review = Review.builder()
                .user(user)
                .product(product)
                .rating(request.getRating())
                .comment(request.getComment())
                .createdDate(LocalDateTime.now())
                .build();

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepository.findProductById(productId);
    }
}
