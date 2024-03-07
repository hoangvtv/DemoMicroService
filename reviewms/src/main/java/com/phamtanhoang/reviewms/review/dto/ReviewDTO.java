package com.phamtanhoang.reviewms.review.dto;

import com.phamtanhoang.reviewms.review.external.Company;

public class ReviewDTO {

  private String title;
  private String description;
  private double rating;
  private Company company;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }
}
