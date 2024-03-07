package com.phamtanhoang.companyms.company;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

  private final CompanyService companyService;

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  @GetMapping
  public ResponseEntity<List<Company>> getAllCompany() {
    return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
    Company company = companyService.getCompanyById(id);
    if (company != null) {
      return new ResponseEntity<>(company, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company updateCompany) {
    companyService.updateCompany(id, updateCompany);
    return new ResponseEntity<>("Company updated successfully", HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> createCompany(@RequestBody Company company) {
    companyService.createCompany(company);
    return new ResponseEntity<>("Company created successfully", HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
    boolean isDeleted = companyService.deleteCompanyById(id);
    if (isDeleted) {
      return new ResponseEntity<>("Company deleted successfully", HttpStatus.OK);
    }
    return new ResponseEntity<>("Company not exists", HttpStatus.NOT_FOUND);
  }

}
