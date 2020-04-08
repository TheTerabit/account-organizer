package pl.bs.accountorganizer.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.Account;
import pl.bs.accountorganizer.models.DetailedAccount;
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

    Account create(AccountMsg accountMsg, DetailedAccount detailedAccount) {
        //checkIfAccountExists().orElse(() -> throw new Exception())
        Account account = new Account(accountMsg.getLogin(), accountMsg.getId(), accountMsg.getEmail(), detailedAccount);
        accountRepository.save(account);
        return account;
    }

    Account create(AccountMsg accountMsg, DetailedAccount detailedAccount, String id) {
        //checkIfAccountExists().orElse(() -> throw new Exception())
        Account account = new Account(id, id, accountMsg.getEmail(), detailedAccount);
        accountRepository.save(account);
        return account;
    }

    void update(String id, AccountMsg accountMsg) {
        Account account = new Account();
        account.setId(id);
        //TODO: initialize Account object
        accountRepository.save(account);
    }

    void delete(String login) {
        accountRepository.deleteByLogin(login);
    }

    String generateNewId() {
        String newId = generateRandomString();
        while (getById(newId) != null) {
            newId = generateRandomString();
        }
        return newId;
    }

    private String generateRandomString() {
        return RandomStringUtils.randomAlphanumeric(6);
    }

    public boolean isLoginUpdated(String id, String login) {
        if (id == null)
            return false;
        if (!login.equals(getLoginById(id)))
            return true;
        else
            return false;
    }

     String getLoginById(String id) {
        Account account = accountRepository.getById(id);
        if (account == null)
            return null;
        else
            return account.getLogin();
    }

}
