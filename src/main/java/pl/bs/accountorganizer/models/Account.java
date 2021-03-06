package pl.bs.accountorganizer.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Account {

    @Id
    @Column(name = "login")
    private String login;
    @Column(unique=true)
    private String id;
    private String email;
    private String previousEmail;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="login", referencedColumnName = "login")
    private DetailedAccount detailedAccount;

    public Account() {
    }

    public Account(String login, String id, String email, DetailedAccount detailedAccount) {
        this.login = login;
        this.id = id;
        this.email = email;
        this.detailedAccount = detailedAccount;
    }

}
