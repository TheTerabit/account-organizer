package pl.bs.accountorganizer.models;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DetailedAccount {

    @Id
    @Column(name = "login")
    private String login;
}
