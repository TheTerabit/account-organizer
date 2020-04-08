package pl.bs.accountorganizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bs.accountorganizer.models.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    public Account getById(String id);

    void deleteByLogin(String login);

    Account getByLogin(String login);
}
