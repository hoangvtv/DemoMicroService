package com.phamtanhoang.jobms.job.dto;

import com.phamtanhoang.jobms.job.Job;
import com.phamtanhoang.jobms.job.external.Company;

public class JobDto {
  private Job job;
  private Company company;

  public Job getJob() {
    return job;
  }

  public void setJob(Job job) {
    this.job = job;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }
}
