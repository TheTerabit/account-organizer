package pl.bs.accountorganizer.services;

import org.springframework.stereotype.Service;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.Account;
import pl.bs.accountorganizer.repositories.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
class AccountService {

    private final AccountRepository accountRepository;

    AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    List<Account> getAll() {
        return accountRepository.findAll();
    }

    Account getById(String id) {
        return accountRepository.findById(id).orElse(null);
    }

    void create(Account account) {
        accountRepository.save(account);
    }

    void update(String id, AccountMsg accountMsg) {
        Account account = new Account();
        account.setId(id);
        //TODO: initialize Account object
        accountRepository.save(account);
    }

    void delete(String id) {
        accountRepository.deleteById(id);
    }
}
