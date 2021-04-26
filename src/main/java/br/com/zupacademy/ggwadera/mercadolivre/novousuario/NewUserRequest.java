package br.com.zupacademy.ggwadera.mercadolivre.novousuario;

import br.com.zupacademy.ggwadera.mercadolivre.util.validation.UniqueValue;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewUserRequest {

    @NotBlank
    @Email
    @UniqueValue(domainClass = User.class, fieldName = "login")
    private String login;

    @NotBlank
    @Size(min = 6)
    private String password;

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

    public User toModel() {
        return new User(login, new PlaintextPassword(password));
    }
}
