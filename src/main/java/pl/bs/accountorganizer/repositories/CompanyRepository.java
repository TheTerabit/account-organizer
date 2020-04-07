package pl.bs.accountorganizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bs.accountorganizer.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    public Company getCompanyByNip(String nip);
}
