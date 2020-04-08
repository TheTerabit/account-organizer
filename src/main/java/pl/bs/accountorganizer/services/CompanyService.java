package pl.bs.accountorganizer.services;


import org.springframework.stereotype.Service;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.Company;
import pl.bs.accountorganizer.repositories.CompanyRepository;

import java.util.ArrayList;

@Service
class CompanyService {

    private final CompanyRepository companyRepository;

    CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    Company create(AccountMsg accountMsg, String id) {
        Company company = new Company(id , normalizeNip(accountMsg.getNip()), accountMsg.getCompanyName(), new ArrayList<>());
        companyRepository.save(company);
        return company;
    }

    String getCompanyLoginByNip(String nip) {
        return companyRepository.getCompanyByNip(normalizeNip(nip)).getLogin();
    }

    boolean companyNotExists(String nip) {
        if (companyRepository.getCompanyByNip(normalizeNip(nip)) == null)
            return true;
        else
            return false;
    }

    private String normalizeNip(String nip) {
        return nip.replaceAll("-","");
    }
}
