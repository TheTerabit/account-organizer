package pl.bs.accountorganizer.services;

import org.springframework.stereotype.Service;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.Account;
import pl.bs.accountorganizer.repositories.AccountRepository;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account getById(String id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Such account does not exist."));
    }

    public void create(AccountMsg accountMsg) {
        Account account = new Account();
        //TODO: initialize Account object
        accountRepository.save(account);
    }

    public void update(String id, AccountMsg accountMsg) {
        Account account = new Account();
        account.setId(id);
        //TODO: initialize Account object
        accountRepository.save(account);
    }

    public void delete(String id) {
        accountRepository.deleteById(id);
    }
}
