package com.phamtanhoang.reviewms.review;


import com.phamtanhoang.reviewms.review.dto.ReviewDTO;
import com.phamtanhoang.reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

  private final ReviewService reviewService;
  private final ReviewMessageProducer reviewMessageProducer;

  public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
    this.reviewService = reviewService;
    this.reviewMessageProducer = reviewMessageProducer;
  }

  @GetMapping
  public ResponseEntity<List<ReviewDTO>> getAllReviews(@RequestParam Long companyId) {
    return new ResponseEntity<>(reviewService.getAllReview(companyId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> createReview(@RequestParam Long companyId,
                                             @RequestBody Review review) {
    boolean isReviewSaved = reviewService.createReview(companyId, review);
    if (isReviewSaved) {
      reviewMessageProducer.sendMessage(review);
      return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
    }
    return new ResponseEntity<>("Company Not Found", HttpStatus.NOT_FOUND);
  }

  @GetMapping("/{reviewId}")
  public ResponseEntity<ReviewDTO> getReview(@PathVariable("reviewId") Long reviewId) {
    return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
  }

  @PutMapping("/{reviewId}")
  public ResponseEntity<String> updateReview(@PathVariable("reviewId") Long reviewId,
                                             @RequestBody Review review) {
    boolean isReviewUpdated = reviewService.updateReview(reviewId, review);

    if (isReviewUpdated) {
      return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
    }
    return new ResponseEntity<>("Review not updated", HttpStatus.NOT_FOUND);

  }

  @DeleteMapping("/{reviewId}")
  public ResponseEntity<String> deleteReview(@PathVariable("reviewId") Long reviewId) {
    boolean isReviewDeleted = reviewService.deleteReview(reviewId);

    if (isReviewDeleted) {
      return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
    return new ResponseEntity<>("Review not deleted", HttpStatus.NOT_FOUND);

  }

  @GetMapping("/averageRating")
  public Double getAverageReview(@RequestParam Long companyId) {
    List<ReviewDTO> reviews = reviewService.getAllReview(companyId);

    return reviews.stream().mapToDouble(ReviewDTO::getRating).average().orElse(0.0);
  }
}
