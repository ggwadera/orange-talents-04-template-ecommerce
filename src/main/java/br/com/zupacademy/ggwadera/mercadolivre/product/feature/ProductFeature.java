package br.com.zupacademy.ggwadera.mercadolivre.product.feature;

import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
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

    @Deprecated
    public ProductFeature() {
    }

    public ProductFeature(String name, String description) {
        Assert.hasLength(name, "nome é obrigatório");
        Assert.hasLength(description, "descrição é obrigatório");
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductFeature that = (ProductFeature) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
