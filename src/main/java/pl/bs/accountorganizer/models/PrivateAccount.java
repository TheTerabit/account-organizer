package pl.bs.accountorganizer.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PrivateAccount extends DetailedAccount{

    private String phoneNumber1;
    private String phoneNumber2;
    @Column(name = "private_user_login")
    private String privateUserLogin;

    public PrivateAccount() {
    }

    public PrivateAccount(String login, String phoneNumber1, String phoneNumber2, String privateUserLogin) {
        setLogin(login);
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.privateUserLogin = privateUserLogin;
    }
}
