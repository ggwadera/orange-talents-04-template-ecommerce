package br.com.zupacademy.ggwadera.mercadolivre.product.feature;

import br.com.zupacademy.ggwadera.mercadolivre.product.Product;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Característica de um produto.
 */
@Entity
public class ProductFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(optional = false)
    private Product product;

    @Deprecated
    public ProductFeature() {
    }

    public ProductFeature(@NotBlank String name, @NotBlank String description, @NotNull Product product) {
        Assert.hasLength(name, "nome é obrigatório");
        Assert.hasLength(description, "descrição é obrigatório");
        Assert.notNull(product, "produto é obrigatório");
        this.name = name;
        this.description = description;
        this.product = product;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductFeature that = (ProductFeature) o;
        return name.equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
