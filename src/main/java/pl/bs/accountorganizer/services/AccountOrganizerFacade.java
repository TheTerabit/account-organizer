package pl.bs.accountorganizer.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountOrganizerFacade {

    private final AccountService accountService;
    private final CompanyService companyService;
    private final CompanyAccountService companyAccountService;
    private final PrivateUserService privateUserService;
    private final PrivateAccountService privateAccountService;

    public AccountOrganizerFacade(AccountService accountService, CompanyService companyService, CompanyAccountService companyAccountService, PrivateUserService privateUserService, PrivateAccountService privateAccountService) {
        this.accountService = accountService;
        this.companyService = companyService;
        this.companyAccountService = companyAccountService;
        this.privateUserService = privateUserService;
        this.privateAccountService = privateAccountService;
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
                accountMsg.getLogin(),
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
        String id = generateNewId();
        Company company = new Company(id ,accountMsg.getNip(), accountMsg.getCompanyName(), new ArrayList<>());
        companyService.create(company);
        Account account = new Account(id, id, accountMsg.getEmail(), company);
        accountService.create(account);
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
        if (privateUserExists(accountMsg))
            createPrivateAccount(accountMsg);
        else
            createPrivateUserAndPrivateAccount(accountMsg);
    }

    private boolean privateUserExists(AccountMsg accountMsg) {
        if (privateUserService.getByNameAndSurnameAndAddress(accountMsg.getName(), accountMsg.getSurname(), accountMsg.getAddress()) != null)
            return true;
        else
            return false;
    }

    private void createPrivateAccount(AccountMsg accountMsg) {
        PrivateAccount privateAccount = new PrivateAccount(
                accountMsg.getLogin(),
                accountMsg.getPhoneNumber1(),
                accountMsg.getPhoneNumber2(),
                privateUserService.getByNameAndSurnameAndAddress(accountMsg.getName(), accountMsg.getSurname(), accountMsg.getAddress()).getLogin());
        privateAccountService.create(privateAccount);
        createAccount(accountMsg, privateAccount);
    }


    private void createPrivateUserAndPrivateAccount(AccountMsg accountMsg) {
        String id = generateNewId();
        PrivateUser privateUser = new PrivateUser(id, accountMsg.getName(), accountMsg.getSurname(), accountMsg.getAddress(), new ArrayList<>());
        privateUserService.create(privateUser);
        Account account = new Account(id, id, accountMsg.getEmail(), privateUser);
        accountService.create(account);
        createPrivateAccount(accountMsg);
    }

    private void createAccount(AccountMsg accountMsg, DetailedAccount detailedAccount) {
        //checkIfAccountExists().orElse(() -> throw new Exception())
        Account account = new Account(accountMsg.getLogin(), accountMsg.getId(), accountMsg.getEmail(), detailedAccount);
        accountService.create(account);
        //return account;
    }
}