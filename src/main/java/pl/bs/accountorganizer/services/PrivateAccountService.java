package pl.bs.accountorganizer.services;

import org.springframework.stereotype.Service;
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

    void create(PrivateAccount privateAccount) {
        privateAccountRepository.save(privateAccount);
    }

    void update(String id, PrivateAccount privateAccount) {
        privateAccount.setLogin(id);
        privateAccountRepository.save(privateAccount);
    }

    void delete(String id) {
        privateAccountRepository.deleteById(id);
    }
}
