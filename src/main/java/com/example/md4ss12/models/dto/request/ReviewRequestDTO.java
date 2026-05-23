package com.example.md4ss12.models.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDTO {
    private Long productId;
    private int rating;
    private String comment;
}
