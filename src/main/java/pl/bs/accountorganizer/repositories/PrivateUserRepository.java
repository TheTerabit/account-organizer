package pl.bs.accountorganizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bs.accountorganizer.models.PrivateUser;

@Repository
public interface PrivateUserRepository extends JpaRepository<PrivateUser, String> {
    public PrivateUser getPrivateUserByNameAndSurnameAndAddress(String name, String surname, String address);
}
