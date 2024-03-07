package com.phamtanhoang.jobms.job.mapper;

import com.phamtanhoang.jobms.job.Job;
import com.phamtanhoang.jobms.job.dto.JobDTO;
import com.phamtanhoang.jobms.job.external.Company;
import com.phamtanhoang.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
  public static JobDTO mapToJobDTO(Job job, Company company, List<Review> reviews) {
    JobDTO jobDto = new JobDTO();
    jobDto.setTitle(job.getTitle());
    jobDto.setDescription(job.getDescription());
    jobDto.setLocation(job.getLocation());
    jobDto.setMinSalary(job.getMinSalary());
    jobDto.setMaxSalary(job.getMaxSalary());
    jobDto.setCompany(company);
    jobDto.setReviews(reviews);

    return jobDto;
  }
}
