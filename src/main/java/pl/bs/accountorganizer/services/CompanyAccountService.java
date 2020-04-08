package pl.bs.accountorganizer.services;

import org.springframework.stereotype.Service;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.CompanyAccount;
import pl.bs.accountorganizer.repositories.CompanyAccountRepository;

@Service
class CompanyAccountService {

    private final CompanyAccountRepository companyAccountRepository;

    CompanyAccountService(CompanyAccountRepository companyAccountRepository) {
        this.companyAccountRepository = companyAccountRepository;
    }

    CompanyAccount create(AccountMsg accountMsg, String companyLogin) {
        CompanyAccount companyAccount = new CompanyAccount(
                accountMsg.getLogin(),
                accountMsg.getName(),
                accountMsg.getSurname(),
                accountMsg.getPhoneNumber1(),
                accountMsg.getPhoneNumber2(),
                accountMsg.getAddress(),
                companyLogin);
        companyAccountRepository.save(companyAccount);
        return companyAccount;
    }
}
