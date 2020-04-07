package pl.bs.accountorganizer.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class PrivateUser extends DetailedAccount{

    private String name;
    private String surname;
    private String address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "private_user_login", referencedColumnName = "login")
    private List<PrivateAccount> privateAccounts;

    public PrivateUser() {
    }

    public PrivateUser(String login, String name, String surname, String address, List<PrivateAccount> privateAccounts) {
        setLogin(login);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.privateAccounts = privateAccounts;
    }
}
