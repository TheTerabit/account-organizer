package pl.bs.accountorganizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bs.accountorganizer.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    public Account getById(String id);

    public void deleteByLogin(String login);

    public Account getByLogin(String login);
}
