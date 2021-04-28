package br.com.zupacademy.ggwadera.mercadolivre.user;

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
    private String email;

    @Column(nullable = false)
    private String password;

    @Generated(GenerationTime.INSERT)
    @Column(nullable = false, columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @Deprecated
    public User() {
    }

    public User(@NotBlank @Email String email, @NotNull PlaintextPassword password) {
        Assert.hasLength(email, "login não pode estar em branco");
        Assert.notNull(password, "senha não pode ser nula");
        this.email = email;
        this.password = password.hash();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
