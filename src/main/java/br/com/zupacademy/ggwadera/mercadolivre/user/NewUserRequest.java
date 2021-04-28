package br.com.zupacademy.ggwadera.mercadolivre.user;

import br.com.zupacademy.ggwadera.mercadolivre.util.validation.UniqueValue;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewUserRequest {

    @NotBlank
    @Email
    @UniqueValue(domainClass = User.class, fieldName = "email")
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User toModel() {
        return new User(email, new PlaintextPassword(password));
    }
}
