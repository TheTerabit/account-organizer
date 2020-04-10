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

    @Transactional
    public void organizeAndCreateAccounts(List<AccountMsg> accountMsgs) {
        accountMsgs.stream()
                .sorted((accountMsg1, accountMsg2) -> compareNullFirst(accountMsg1.getId(), accountMsg2.getId()))
                .forEach(accountMsg -> organizeAndCreate(accountMsg));
    }

    private int compareNullFirst(String id1, String id2) {
        if ((id1 == null) && (id2 == null))
            return 0;
        else if (id1 == null)
            return -1;
        else if (id2 == null)
            return 1;
        else
            return 0;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void organizeAndCreate(AccountMsg accountMsg) {
        validate(accountMsg);
        if (isLoginUpdated(accountMsg))
            deleteAccount(accountMsg);
        createNewAccount(accountMsg);
    }

    private void validate(AccountMsg accountMsg) {
        if(accountMsg.getLogin() == null)
            throw new BadRequestException("Login can not be null.");
        if((accountMsg.getNip() == null) && ((accountMsg.getName() == null) || (accountMsg.getSurname() == null)||(accountMsg.getAddress() == null))) {
            throw new BadRequestException("User account can not be created because of missing data.");
        }
    }

    private boolean isLoginUpdated(AccountMsg accountMsg) {
        return accountService.isLoginUpdated(accountMsg.getId(), accountMsg.getLogin());
    }

    private void deleteAccount(AccountMsg accountMsg) {
        accountService.delete(accountService.getLoginById(accountMsg.getId()));
    }

    private void createNewAccount(AccountMsg accountMsg) {
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
        accountService.createParent(accountMsg, company, id);
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
        accountService.createParent(accountMsg, privateUser, id);
    }

    private void createPrivateAccount(AccountMsg accountMsg) {
        String privateUserLogin = privateUserService.getPrivateUserLogin(accountMsg.getName(), accountMsg.getSurname(), accountMsg.getAddress());
        PrivateAccount privateAccount = privateAccountService.create(accountMsg, privateUserLogin);
        accountService.create(accountMsg, privateAccount);
    }

}