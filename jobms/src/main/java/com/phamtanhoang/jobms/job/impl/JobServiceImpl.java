package com.phamtanhoang.jobms.job.impl;

import com.phamtanhoang.jobms.job.Job;
import com.phamtanhoang.jobms.job.JobRepository;
import com.phamtanhoang.jobms.job.JobService;
import com.phamtanhoang.jobms.job.clients.CompanyClient;
import com.phamtanhoang.jobms.job.clients.ReviewClient;
import com.phamtanhoang.jobms.job.dto.JobDTO;
import com.phamtanhoang.jobms.job.external.Company;
import com.phamtanhoang.jobms.job.external.Review;
import com.phamtanhoang.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class JobServiceImpl implements JobService {

  private final JobRepository jobRepository;
  //  private final RestTemplate restTemplate = new RestTemplate();
  private final RestTemplate restTemplate;
  private final CompanyClient companyClient;
  private final ReviewClient reviewClient;

  int attempt=0;


  public JobServiceImpl(JobRepository jobRepository, RestTemplate restTemplate, CompanyClient companyClient, ReviewClient reviewClient) {
    this.jobRepository = jobRepository;
    this.restTemplate = restTemplate;
    this.companyClient = companyClient;
    this.reviewClient = reviewClient;
  }


  @Override
//  @CircuitBreaker(name="companyBreaker",
//      fallbackMethod = "companyBeakerFallback")
//  @Retry(name = "companyBreaker",
//      fallbackMethod = "companyBeakerFallback")
  @RateLimiter(name = "companyBreaker")
//      fallbackMethod = "companyBeakerFallback")
  public List<JobDTO> findAll() {
    System.out.println("attempt" + ++ attempt);
    List<Job> jobs = jobRepository.findAll();

    return jobs.stream().map(this::convertToJobDTO).collect(Collectors.toList());
  }

  @Override
  public void createJob(Job job) {
    jobRepository.save(job);
  }

  @Override
  public JobDTO getJobById(Long id) {
    Job job = jobRepository.findById(id).orElse(null);
    return convertToJobDTO(job);
  }

  @Override
  public boolean deleteJobById(Long id) {
    if (jobRepository.existsById(id)) {
      jobRepository.deleteById(id);
      return true;
    }
    return false;
  }

  @Override
  public boolean updateJob(Long id, Job updateJob) {
    Optional<Job> jobOptional = jobRepository.findById(id);
    if (jobOptional.isPresent()) {
      Job job = jobOptional.get();
      job.setTitle(updateJob.getTitle());
      job.setDescription(updateJob.getDescription());
      job.setMinSalary(updateJob.getMinSalary());
      job.setMaxSalary(updateJob.getMaxSalary());
      job.setLocation(updateJob.getLocation());

      jobRepository.save(job);
      return true;
    }
    return false;
  }

  private JobDTO convertToJobDTO(Job job) {
//    Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/api/v1/companies/" +
//        job.getCompanyId(), Company.class);
    Company company = companyClient.getCompany(job.getCompanyId());

//    ResponseEntity<List<Review>> reviewResponse =
//        restTemplate.exchange("http://REVIEW-SERVICE:8083/api/v1/reviews?companyId=" +
//                job.getCompanyId(),
//            HttpMethod.GET,
//            null,
//            new ParameterizedTypeReference<List<Review>>() {
//            });
//    List<Review> reviews = reviewResponse.getBody();

    List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

    JobDTO jobDto = JobMapper.mapToJobDTO(job, company, reviews);

    return jobDto;
  }

  public List<String> companyBeakerFallback(Exception e) {
    List<String> list = new ArrayList<>();
    list.add("Dummy");
    return list;
  }

}
