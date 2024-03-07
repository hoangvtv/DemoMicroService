package com.phamtanhoang.reviewms.review;

import com.phamtanhoang.reviewms.review.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
  List<ReviewDto> getAllReview(Long companyId);

  boolean createReview(Long companyId, Review review);

  Review getReview(Long reviewId);

  boolean updateReview(Long reviewId, Review review);

  boolean deleteReview(Long reviewId);
}
