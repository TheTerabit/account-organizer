package pl.bs.accountorganizer.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class CompanyAccount extends DetailedAccount {

    private String name;
    private String surname;
    private String phoneNumber1;
    private String phoneNumber2;
    private String address;
    @Column(name = "company_login")
    private String companyLogin;

    public CompanyAccount() {
    }

}
