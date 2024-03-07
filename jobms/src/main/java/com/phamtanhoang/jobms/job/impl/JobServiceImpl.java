package com.phamtanhoang.jobms.job.impl;

import com.phamtanhoang.jobms.job.Job;
import com.phamtanhoang.jobms.job.JobRepository;
import com.phamtanhoang.jobms.job.JobService;
import com.phamtanhoang.jobms.job.dto.JobDto;
import com.phamtanhoang.jobms.job.external.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class JobServiceImpl implements JobService {

  private final JobRepository jobRepository;
//  private final RestTemplate restTemplate = new RestTemplate();


  private final RestTemplate restTemplate;


  public JobServiceImpl(JobRepository jobRepository, RestTemplate restTemplate) {
    this.jobRepository = jobRepository;
    this.restTemplate = restTemplate;
  }


  @Override
  public List<JobDto> findAll() {
    List<Job> jobs = jobRepository.findAll();

    return jobs.stream().map(this::convertToJobDto).collect(Collectors.toList());
  }

  @Override
  public void createJob(Job job) {
    jobRepository.save(job);
  }

  @Override
  public Job getJobById(Long id) {
    return jobRepository.findById(id).orElse(null);
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

  private JobDto convertToJobDto(Job job) {
    JobDto jobDto = new JobDto();
    jobDto.setJob(job);

    Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/api/v1/companies/" +
            job.getCompanyId(),
        Company.class);
    jobDto.setCompany(company);

    return jobDto;
  }

}
