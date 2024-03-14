package com.phamtanhoang.companyms.company.iml;

import com.phamtanhoang.companyms.company.Company;
import com.phamtanhoang.companyms.company.CompanyRepository;
import com.phamtanhoang.companyms.company.CompanyService;
import com.phamtanhoang.companyms.company.clients.ReviewClient;
import com.phamtanhoang.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;
  private final ReviewClient reviewClient;

  public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
    this.companyRepository = companyRepository;
    this.reviewClient = reviewClient;
  }

  @Override
  public List<Company> getAllCompanies() {
    return companyRepository.findAll();
  }

  @Override
  public void createCompany(Company company) {
    companyRepository.save(company);
  }

  @Override
  public Company getCompanyById(Long id) {
    return companyRepository.findById(id).orElse(null);
  }

  @Override
  public boolean deleteCompanyById(Long id) {
    if (companyRepository.existsById(id)) {
      companyRepository.deleteById(id);
      return true;
    }
    return false;
  }

  @Override
  public boolean updateCompany(Long id, Company updateCompany) {
    Optional<Company> companyOptional = companyRepository.findById(id);
    if (companyOptional.isPresent()) {
      Company company = companyOptional.get();
      company.setName(updateCompany.getName());
      company.setDescription(updateCompany.getDescription());
//      company.setJobs(updateCompany.getJobs());

      companyRepository.save(company);
      return true;
    }
    return false;
  }

  @Override
  public void updateCompanyRating(ReviewMessage reviewMessage) {

    Company company = companyRepository.findById(reviewMessage.getCompanyId())
        .orElseThrow(() -> new NotFoundException("Company not found " +
            reviewMessage.getCompanyId()));

    double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());

    company.setRating(averageRating);

    companyRepository.save(company);
  }
}
