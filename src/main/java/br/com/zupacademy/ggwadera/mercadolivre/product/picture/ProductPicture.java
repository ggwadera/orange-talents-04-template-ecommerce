package br.com.zupacademy.ggwadera.mercadolivre.product.picture;

import br.com.zupacademy.ggwadera.mercadolivre.product.Product;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class ProductPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @URL
    @NaturalId
    @Column(nullable = false, columnDefinition = "text")
    private String uri;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;

    @Deprecated
    public ProductPicture() {
    }

    public ProductPicture(@NotBlank String uri, @NotNull Product product) {
        this.uri = uri;
        this.product = product;
    }

    public String getUri() {
        return uri;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPicture that = (ProductPicture) o;
        return uri.equals(that.getUri()) && product.equals(that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, product);
    }
}
