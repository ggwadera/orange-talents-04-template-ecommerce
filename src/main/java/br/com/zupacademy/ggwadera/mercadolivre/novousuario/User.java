package br.com.zupacademy.ggwadera.mercadolivre.novousuario;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.NaturalId;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Generated(GenerationTime.INSERT)
    @Column(nullable = false, columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @Deprecated
    public User() {
    }

    public User(@NotBlank @Email String login, @NotNull PlaintextPassword password) {
        Assert.hasLength(login, "login não pode estar em branco");
        Assert.notNull(password, "senha não pode ser nula");
        this.login = login;
        this.password = password.hash();
    }

}
