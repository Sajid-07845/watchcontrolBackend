package com.watchstore.service;

import com.watchstore.model.Review;
import java.util.List;

public interface ReviewService {
    Review addReview(Long productId, Long userId, Review review);
    List<Review> getReviewsByProduct(Long productId);
}
