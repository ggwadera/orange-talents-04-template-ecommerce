package br.com.zupacademy.ggwadera.mercadolivre.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    private final String password;

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(this.email, this.password);
    }
}
