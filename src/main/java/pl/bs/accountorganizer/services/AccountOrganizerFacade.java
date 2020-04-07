package pl.bs.accountorganizer.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.*;

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

    public void organizeAndCreateAccounts(List<AccountMsg> accountMsgs) {
        accountMsgs.stream().forEach(accountMsg -> organizeAndCreate(accountMsg));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    void organizeAndCreate(AccountMsg accountMsg) {
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
        if (companyNotExists(accountMsg))
            createCompany(accountMsg);
        createCompanyAccount(accountMsg);
    }

    private boolean companyNotExists(AccountMsg accountMsg) {
        return companyService.companyNotExists(accountMsg.getNip());
    }

    private void createCompany(AccountMsg accountMsg) {
        String id = accountService.generateNewId();
        Company company = companyService.create(accountMsg, id);
        accountService.create(accountMsg, company, id);
    }

    private void createCompanyAccount(AccountMsg accountMsg) {
        String companyLogin = companyService.getCompanyLoginByNip(accountMsg.getNip());
        CompanyAccount companyAccount = companyAccountService.create(accountMsg, companyLogin);
        accountService.create(accountMsg, companyAccount);
    }

    private void organizeAndCreatePersonalAccounts(AccountMsg accountMsg) {
        if (privateUserNotExists(accountMsg))
            createPrivateUser(accountMsg);
        createPrivateAccount(accountMsg);
    }

    private boolean privateUserNotExists(AccountMsg accountMsg) {
        return privateUserService.privateUserNotExists(accountMsg.getName(), accountMsg.getSurname(), accountMsg.getAddress());
    }

    private void createPrivateUser(AccountMsg accountMsg) {
        String id = accountService.generateNewId();
        PrivateUser privateUser = privateUserService.create(accountMsg, id);
        accountService.create(accountMsg, privateUser, id);
    }

    private void createPrivateAccount(AccountMsg accountMsg) {
        String privateUserLogin = privateUserService.getPrivateUserLogin(accountMsg.getName(), accountMsg.getSurname(), accountMsg.getAddress());
        PrivateAccount privateAccount = privateAccountService.create(accountMsg, privateUserLogin);
        accountService.create(accountMsg, privateAccount);
    }

}