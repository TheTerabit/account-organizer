package pl.bs.accountorganizer.controllers;

import org.springframework.web.bind.annotation.*;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.Account;
import pl.bs.accountorganizer.services.AccountOrganizerFacade;

import java.util.List;

@RestController
@RequestMapping("/")
public class AccountOrganizerEndpoint {

    private final AccountOrganizerFacade accountOrganizerFacade;

    public AccountOrganizerEndpoint(AccountOrganizerFacade accountOrganizerFacade) {
        this.accountOrganizerFacade = accountOrganizerFacade;
    }

    @GetMapping
    public List<Account> getAll() {
        return accountOrganizerFacade.getAll();
    }

    @PostMapping
    public void organizeAndCreateAccounts(@RequestBody List<AccountMsg> accountMsgs) {
        accountOrganizerFacade.organizeAndCreateAccounts(accountMsgs);
    }

    @PostMapping("/new")
    public void createAccount(@RequestBody AccountMsg accountMsg) {
        accountOrganizerFacade.organizeAndCreate(accountMsg);
    }

}
