package pl.bs.accountorganizer.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DetailedAccount {

    @Id
    @Column(name = "login")
    private String login;
}
