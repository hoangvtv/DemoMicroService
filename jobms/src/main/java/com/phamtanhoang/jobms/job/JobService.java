package com.phamtanhoang.jobms.job;


import com.phamtanhoang.jobms.job.dto.JobDto;

import java.util.List;

public interface JobService {
  List<JobDto> findAll();
  void createJob(Job job);

  Job getJobById(Long id);

  boolean deleteJobById(Long id);

  boolean updateJob(Long id, Job updateJob);
}
