package pl.bs.accountorganizer.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.Account;
import pl.bs.accountorganizer.models.Company;
import pl.bs.accountorganizer.models.CompanyAccount;
import pl.bs.accountorganizer.models.DetailedAccount;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountOrganizerFacade {

    private final AccountService accountService;
    private final CompanyService companyService;
    private final CompanyAccountService companyAccountService;

    public AccountOrganizerFacade(AccountService accountService, CompanyService companyService, CompanyAccountService companyAccountService) {
        this.accountService = accountService;
        this.companyService = companyService;
        this.companyAccountService = companyAccountService;
    }

    public List<Account> getAll() {
        return accountService.getAll();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void organizeAndCreateAccounts(List<AccountMsg> accountMsgs) {
        accountMsgs.stream().forEach(accountMsg -> organizeAndCreate(accountMsg));
    }

    private void organizeAndCreate(AccountMsg accountMsg) {
        if (accountMsgHasNip(accountMsg))
            organizeAndCreateCompanyAccount(accountMsg);
        else
            organizeAndCreatePersonalAccounts(accountMsg);
    }

    private boolean accountMsgHasNip(AccountMsg accountMsg) {
        if (accountMsg.getNip() != null)
            return true;
        else
            return false;
    }

    private void organizeAndCreateCompanyAccount(AccountMsg accountMsg) {
        if (companyExists(accountMsg))
            createCompanyAccount(accountMsg);
        else
            createCompanyAndCompanyAccount(accountMsg);
    }


    private boolean companyExists(AccountMsg accountMsg) {
        if (companyService.getByNip(accountMsg.getNip()) != null)
            return true;
        else
            return false;
    }

    private void createCompanyAccount(AccountMsg accountMsg) {
        CompanyAccount companyAccount = new CompanyAccount(
                accountMsg.getName(),
                accountMsg.getSurname(),
                accountMsg.getPhoneNumber1(),
                accountMsg.getPhoneNumber2(),
                accountMsg.getAddress(),
                companyService.getByNip(accountMsg.getNip()).getLogin());
        companyAccountService.create(companyAccount);
        createAccount(accountMsg, companyAccount);
    }

    private void createCompanyAndCompanyAccount(AccountMsg accountMsg) {
        Company company = new Company(accountMsg.getNip(), accountMsg.getCompanyName(), new ArrayList<>());
        companyService.create(company);
        Account account = new Account(generateNewId(), generateNewId(), accountMsg.getEmail(), company);
        createAccount(accountMsg, company);
        createCompanyAccount(accountMsg);
    }

    private String generateNewId() {
        String newId = generateRandomString();
        while (accountService.getById(newId) != null) {
            newId = generateRandomString();
        }
        return newId;
    }

    private String generateRandomString() {
        return RandomStringUtils.randomAlphanumeric(6);
    }


    private void organizeAndCreatePersonalAccounts(AccountMsg accountMsg) {
    }

    private void createAccount(AccountMsg accountMsg, DetailedAccount detailedAccount) {
        //checkIfAccountExists().orElse(() -> throw new Exception())
        Account account = new Account(accountMsg.getLogin(), accountMsg.getId(), accountMsg.getEmail(), detailedAccount);
        accountService.create(account);
        //return account;
    }
}
