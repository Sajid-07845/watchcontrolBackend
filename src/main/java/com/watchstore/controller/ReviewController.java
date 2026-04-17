package com.watchstore.controller;

import com.watchstore.model.Review;
import com.watchstore.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{productId}")
    public ResponseEntity<Review> addReview(@PathVariable Long productId, @RequestParam Long userId, @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.addReview(productId, userId, review));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<Review>> getReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProduct(productId));
    }
}
