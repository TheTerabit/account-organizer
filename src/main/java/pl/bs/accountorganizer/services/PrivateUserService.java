package pl.bs.accountorganizer.services;

import org.springframework.stereotype.Service;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.PrivateUser;
import pl.bs.accountorganizer.repositories.PrivateUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
class PrivateUserService {

    private final PrivateUserRepository privateUserRepository;

    PrivateUserService(PrivateUserRepository privateUserRepository) {
        this.privateUserRepository = privateUserRepository;
    }

    List<PrivateUser> getAll() {
        return privateUserRepository.findAll();
    }

    PrivateUser getById(String id) {
        return privateUserRepository.findById(id).orElseThrow(() -> new RuntimeException("Such privateUser does not exist."));
    }

    String getPrivateUserLogin(String name, String surname, String address) {
        return privateUserRepository.getPrivateUserByNameAndSurnameAndAddress(name, surname, address).getLogin();
    }

    PrivateUser create(AccountMsg accountMsg, String id) {
        PrivateUser privateUser = new PrivateUser(id, accountMsg.getName(), accountMsg.getSurname(), accountMsg.getAddress(), new ArrayList<>());
        privateUserRepository.save(privateUser);
        return privateUser;
    }

    void update(String id, PrivateUser privateUser) {
        privateUser.setLogin(id);
        privateUserRepository.save(privateUser);
    }

    void delete(String id) {
        privateUserRepository.deleteById(id);
    }

    public boolean privateUserNotExists(String name, String surname, String address) {
        if (privateUserRepository.getPrivateUserByNameAndSurnameAndAddress(name, surname, address) == null)
            return true;
        else
            return false;
    }
}
