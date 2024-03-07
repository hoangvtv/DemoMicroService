package com.phamtanhoang.jobms.job;


import com.phamtanhoang.jobms.job.dto.JobDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

  private final JobService jobService;

  public JobController(JobService jobService) {
    this.jobService = jobService;
  }

  @GetMapping()
  public ResponseEntity<List<JobDto>> findAll() {

    return ResponseEntity.of(Optional.ofNullable(jobService.findAll()));
  }

  @PostMapping()
  public ResponseEntity<String> createJob(@RequestBody Job job) {
    jobService.createJob(job);
//    Company company = job.getCompany();

    return new ResponseEntity<>("Job added successfully", HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Job> getJobById(@PathVariable("id") Long id) {
    Job job = jobService.getJobById(id);

    if (job != null) {
      return new ResponseEntity<>(job, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteJobById(@PathVariable("id") Long id) {
    boolean deleted = jobService.deleteJobById(id);
    if (deleted) {
      return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
    }

    return new ResponseEntity<>("Job is not exist", HttpStatus.NOT_FOUND);
  }

  @PutMapping("/{id}")
//  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity<String> updateJob(@PathVariable("id") Long id,
                                          @RequestBody Job updateJob) {
    boolean updated = jobService.updateJob(id, updateJob);
    if (updated) {
      return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
    }

    return new ResponseEntity<>("Job is not exist", HttpStatus.NOT_FOUND);
  }

}
