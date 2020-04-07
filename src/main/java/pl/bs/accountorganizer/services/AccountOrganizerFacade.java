package pl.bs.accountorganizer.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.Account;

import java.util.List;

@Service
public class AccountOrganizerFacade {

    private final AccountService accountService;

    public AccountOrganizerFacade(AccountService accountService) {
        this.accountService = accountService;
    }

    public List<Account> getAll() {
        return accountService.getAll();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void organizeAndCreateAccounts(List<AccountMsg> accountMsgs) {
        accountMsgs.stream().forEach(accountMsg -> organizeAndCreate(accountMsg));
    }

    private void organizeAndCreate(AccountMsg accountMsg) {

    }
}
