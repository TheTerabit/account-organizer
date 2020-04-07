package pl.bs.accountorganizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bs.accountorganizer.models.PrivateAccount;

@Repository
public interface PrivateAccountRepository extends JpaRepository<PrivateAccount, String> {
}
