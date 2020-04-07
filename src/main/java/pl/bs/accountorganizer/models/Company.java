package pl.bs.accountorganizer.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Company extends DetailedAccount{

    private String nip;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "company_login", referencedColumnName = "login")
    private List<CompanyAccount> companyAccounts;

    public Company() {
    }

}
