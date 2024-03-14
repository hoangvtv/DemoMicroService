package com.phamtanhoang.companyms.company;

import com.phamtanhoang.companyms.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
  List<Company> getAllCompanies();
  void createCompany(Company company);

  Company getCompanyById(Long id);

  boolean deleteCompanyById(Long id);

  boolean updateCompany(Long id, Company updateCompany);
  void updateCompanyRating(ReviewMessage reviewMessaged);

}
