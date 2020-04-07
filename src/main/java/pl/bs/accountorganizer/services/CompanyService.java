package pl.bs.accountorganizer.services;


import org.springframework.stereotype.Service;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.Company;
import pl.bs.accountorganizer.repositories.CompanyRepository;

import java.util.ArrayList;
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

    String getCompanyLoginByNip(String nip) {
        return companyRepository.getCompanyByNip(nip).getLogin();
    }

    Company create(AccountMsg accountMsg, String id) {
        Company company = new Company(id ,accountMsg.getNip(), accountMsg.getCompanyName(), new ArrayList<>());
        companyRepository.save(company);
        return company;
    }

    void update(String id, Company company) {
        company.setLogin(id);
        companyRepository.save(company);
    }

    public void delete(String id) {
        companyRepository.deleteById(id);
    }

    public boolean companyNotExists(String nip) {
        if (companyRepository.getCompanyByNip(nip) == null)
            return true;
        else
            return false;
    }
}
