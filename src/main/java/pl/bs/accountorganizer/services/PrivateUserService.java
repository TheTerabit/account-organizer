package pl.bs.accountorganizer.services;

import org.springframework.stereotype.Service;
import pl.bs.accountorganizer.models.PrivateUser;
import pl.bs.accountorganizer.repositories.PrivateUserRepository;

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

    PrivateUser getByNameAndSurnameAndAddress(String name, String surname, String address) {
        return privateUserRepository.getPrivateUserByNameAndSurnameAndAddress(name, surname, address);
    }

    void create(PrivateUser privateUser) {
        privateUserRepository.save(privateUser);
    }

    void update(String id, PrivateUser privateUser) {
        privateUser.setLogin(id);
        privateUserRepository.save(privateUser);
    }

    void delete(String id) {
        privateUserRepository.deleteById(id);
    }
}
