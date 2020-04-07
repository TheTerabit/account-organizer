package pl.bs.accountorganizer.services;


import org.springframework.stereotype.Service;
import pl.bs.accountorganizer.models.Company;
import pl.bs.accountorganizer.repositories.CompanyRepository;

import java.util.List;

@Service
class CompanyService {

    private final CompanyRepository companyRepository;

    CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    List<Company> getAll() {
        return companyRepository.findAll();
    }

    Company getById(String id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Such company does not exist."));
    }

    Company getByNip(String nip) {
        return companyRepository.getCompanyByNip(nip);
    }

    void create(Company company) {
        companyRepository.save(company);
    }

    void update(String id, Company company) {
        company.setLogin(id);
        companyRepository.save(company);
    }

    public void delete(String id) {
        companyRepository.deleteById(id);
    }
}
