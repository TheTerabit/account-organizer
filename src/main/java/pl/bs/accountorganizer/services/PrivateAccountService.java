package pl.bs.accountorganizer.services;

import org.springframework.stereotype.Service;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.PrivateAccount;
import pl.bs.accountorganizer.repositories.PrivateAccountRepository;

@Service
class PrivateAccountService {

    private final PrivateAccountRepository privateAccountRepository;

    PrivateAccountService(PrivateAccountRepository privateAccountRepository) {
        this.privateAccountRepository = privateAccountRepository;
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
}
