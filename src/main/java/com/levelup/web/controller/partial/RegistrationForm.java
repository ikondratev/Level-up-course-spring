package com.levelup.web.controller.partial;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class RegistrationForm {

    @NotEmpty(message = "user's login can't be empty")
    @Email(message = "format must be in format: ****@***.***")
    private String login;

    @NotEmpty(message = "password can't be blank")
    @Length(max = 50)
    private String password;

    @NotEmpty(message = "confirmation password can't be blank")
    @Length(max = 50)
    private String confirmation;

    public RegistrationForm() {
    }

    public RegistrationForm(String login, String password, String confirmation) {
        this.login = login;
        this.password = password;
        this.confirmation = confirmation;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
}
