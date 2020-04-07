package pl.bs.accountorganizer.services;

import org.springframework.stereotype.Service;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.PrivateAccount;
import pl.bs.accountorganizer.repositories.PrivateAccountRepository;

import java.util.List;

@Service
class PrivateAccountService {

    private final PrivateAccountRepository privateAccountRepository;

    PrivateAccountService(PrivateAccountRepository privateAccountRepository) {
        this.privateAccountRepository = privateAccountRepository;
    }

    List<PrivateAccount> getAll() {
        return privateAccountRepository.findAll();
    }

    PrivateAccount getById(String id) {
        return privateAccountRepository.findById(id).orElseThrow(() -> new RuntimeException("Such privateAccount does not exist."));
    }

    PrivateAccount create(AccountMsg accountMsg, String privateUserLogin) {
        PrivateAccount privateAccount = new PrivateAccount(
                accountMsg.getLogin(),
                accountMsg.getPhoneNumber1(),
                accountMsg.getPhoneNumber2(),
                privateUserLogin);
        privateAccountRepository.save(privateAccount);
        return privateAccount;
    }

    void update(String id, PrivateAccount privateAccount) {
        privateAccount.setLogin(id);
        privateAccountRepository.save(privateAccount);
    }

    void delete(String id) {
        privateAccountRepository.deleteById(id);
    }
}
