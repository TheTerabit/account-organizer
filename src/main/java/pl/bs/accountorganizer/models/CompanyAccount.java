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

    public CompanyAccount(String login, String name, String surname, String phoneNumber1, String phoneNumber2, String address, String companyLogin) {
        setLogin(login);
        this.name = name;
        this.surname = surname;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.address = address;
        this.companyLogin = companyLogin;
    }
}
