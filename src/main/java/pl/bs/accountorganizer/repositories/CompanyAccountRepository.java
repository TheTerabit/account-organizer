package pl.bs.accountorganizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bs.accountorganizer.models.CompanyAccount;

@Repository
public interface CompanyAccountRepository extends JpaRepository<CompanyAccount, String> {
}
