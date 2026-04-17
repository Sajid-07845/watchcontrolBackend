package com.watchstore.service.impl;

import com.watchstore.exception.ResourceNotFoundException;
import com.watchstore.model.Product;
import com.watchstore.model.Review;
import com.watchstore.model.User;
import com.watchstore.repository.ProductRepository;
import com.watchstore.repository.ReviewRepository;
import com.watchstore.repository.UserRepository;
import com.watchstore.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public Review addReview(Long productId, Long userId, Review review) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        review.setProduct(product);
        review.setUser(user);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return reviewRepository.findByProduct(product);
    }
}
