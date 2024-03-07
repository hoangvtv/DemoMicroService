package com.phamtanhoang.reviewms.review;

import com.phamtanhoang.reviewms.review.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
  List<ReviewDTO> getAllReview(Long companyId);

  boolean createReview(Long companyId, Review review);

  ReviewDTO getReview(Long reviewId);

  boolean updateReview(Long reviewId, Review review);

  boolean deleteReview(Long reviewId);
}
