package br.com.zupacademy.ggwadera.mercadolivre.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PlaintextPassword {

  private final String password;

  public PlaintextPassword(@NotBlank @Size(min = 6) String password) {
    Assert.hasLength(password, "senha não pode ser em branco");
    Assert.isTrue(password.length() >= 6, "senha deve ter no mínimo 6 caracteres");
    this.password = password;
  }

  public String hash() {
    return new BCryptPasswordEncoder().encode(password);
  }
}
