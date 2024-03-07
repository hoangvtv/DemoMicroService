package com.phamtanhoang.reviewms.review.dto;

import com.phamtanhoang.reviewms.review.Review;
import com.phamtanhoang.reviewms.review.external.Company;

public class ReviewDto {
  private Review review;
  private Company company;

  public Review getReview() {
    return review;
  }

  public void setReview(Review review) {
    this.review = review;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }
}
