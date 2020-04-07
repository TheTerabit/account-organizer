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
    private String id;
    private String email;
    @OneToOne
    @JoinColumn(name="login", referencedColumnName = "login")
    private DetailedAccount detailedAccount;

    public Account() {
    }

}