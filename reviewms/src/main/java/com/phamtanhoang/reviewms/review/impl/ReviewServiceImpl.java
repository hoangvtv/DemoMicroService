package com.phamtanhoang.reviewms.review.impl;

import com.phamtanhoang.reviewms.review.Review;
import com.phamtanhoang.reviewms.review.ReviewRepository;
import com.phamtanhoang.reviewms.review.ReviewService;
import com.phamtanhoang.reviewms.review.dto.ReviewDto;
import com.phamtanhoang.reviewms.review.external.Company;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;

  private final RestTemplate restTemplate;


  public ReviewServiceImpl(ReviewRepository reviewRepository, RestTemplate restTemplate) {
    this.reviewRepository = reviewRepository;
    this.restTemplate = restTemplate;
  }

  @Override
  public List<ReviewDto> getAllReview(Long companyId) {
    List<Review> reviews= reviewRepository.findAll();
    return reviews.stream().map(this::convertToReviewDto).collect(Collectors.toList());
  }

  @Override
  public boolean createReview(Long companyId, Review review) {

    if (companyId != null && review != null) {
      review.setCompanyId(companyId);
      reviewRepository.save(review);
      return true;
    }
    return false;
  }

  @Override
  public Review getReview(Long reviewId) {
    return reviewRepository.findById(reviewId).orElse(null);
  }

  @Override
  public boolean updateReview(Long reviewId, Review updateReview) {
    Review review = reviewRepository.findById(reviewId).orElse(null);

    if (reviewId != null) {
      review.setTitle(updateReview.getTitle());
      review.setDescription(updateReview.getDescription());
      review.setRating(updateReview.getRating());
      review.setCompanyId(updateReview.getCompanyId());
      reviewRepository.save(review);

      return true;
    }

    return false;
  }

  @Override
  @Transactional
  public boolean deleteReview(Long reviewId) {
    Review review = reviewRepository.findById(reviewId).orElse(null);

    if (review != null) {
      reviewRepository.delete(review);

      return true;
    }

    return false;
  }

  private ReviewDto convertToReviewDto(Review review) {
    ReviewDto reviewDto= new ReviewDto();
    reviewDto.setReview(review);

    Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/api/v1/companies/" +
            review.getCompanyId(),
        Company.class);
    reviewDto.setCompany(company);

    return reviewDto;
  }

}
