package com.phamtanhoang.reviewms.review.clients;

import com.phamtanhoang.reviewms.review.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COMPANY-SERVICE")
public interface CompanyClient {

  @GetMapping("/api/v1/companies/{id}")
  Company getCompany(@PathVariable("id") Long id);

}
