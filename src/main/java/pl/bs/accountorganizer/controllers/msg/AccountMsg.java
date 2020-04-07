package pl.bs.accountorganizer.controllers.msg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AccountMsg {

    private final String name;
    private final String surname;
    private final String nip;
    private final String companyName;
    private final String email;
    private final String phoneNumber1;
    private final String phoneNumber2;
    private final String login;
    private final String id;
    private final String address;

    @JsonCreator
    public AccountMsg(@JsonProperty("name") String name,
                      @JsonProperty("surname") String surname,
                      @JsonProperty("nip") String nip,
                      @JsonProperty("companyName") String companyName,
                      @JsonProperty("email") String email,
                      @JsonProperty("phoneNumber1") String phoneNumber1,
                      @JsonProperty("phoneNumber2") String phoneNumber2,
                      @JsonProperty("login") String login,
                      @JsonProperty("id") String id,
                      @JsonProperty("address") String address) {
        this.name = name;
        this.surname = surname;
        this.nip = nip;
        this.companyName = companyName;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.login = login;
        this.id = id;
        this.address = address;
        this.email = email;
    }
}
