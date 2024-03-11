package com.phamtanhoang.reviewms.review.mapper;

import com.phamtanhoang.reviewms.review.Review;
import com.phamtanhoang.reviewms.review.dto.ReviewDTO;
import com.phamtanhoang.reviewms.review.external.Company;

public class ReviewMapper {

  public static ReviewDTO mapToReviewDTO(
      Review review,
      Company company
  ) {

    ReviewDTO reviewDto = new ReviewDTO();
    reviewDto.setTitle(review.getTitle());
    reviewDto.setDescription(review.getDescription());
    reviewDto.setRating(review.getRating());
    reviewDto.setCompany(company);

    return reviewDto;
  }

}
