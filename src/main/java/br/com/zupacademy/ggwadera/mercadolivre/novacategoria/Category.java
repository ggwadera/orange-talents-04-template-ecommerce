package br.com.zupacademy.ggwadera.mercadolivre.novacategoria;

import org.hibernate.annotations.NaturalId;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Category parent;

    @Deprecated
    public Category() {
    }

    public Category(@NotBlank String name) {
        Assert.hasLength(name, "nome não pode estar em branco");
        this.name = name;
    }

    public Category(@NotBlank String name, @NotNull Category parent) {
        this(name);
        Assert.notNull(parent, "categoria mãe não pode ser nula");
        this.parent = parent;
    }
}
