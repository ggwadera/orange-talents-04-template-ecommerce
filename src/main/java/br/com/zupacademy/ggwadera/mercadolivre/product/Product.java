package br.com.zupacademy.ggwadera.mercadolivre.product;

import br.com.zupacademy.ggwadera.mercadolivre.category.Category;
import br.com.zupacademy.ggwadera.mercadolivre.product.feature.ProductFeature;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private Integer quantity;

    @Column(columnDefinition = "text", length = 1000, nullable = false)
    private String description;

    @ManyToOne(optional = false)
    private Category category;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Set<ProductFeature> features;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    public static final class Builder {
        private String name;
        private BigDecimal value;
        private Integer quantity;
        private String description;
        private Category category;
        private Set<ProductFeature> features;
        private User user;

        public Builder() {}

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withValue(BigDecimal value) {
            this.value = value;
            return this;
        }

        public Builder withQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public Builder withFeatures(Set<ProductFeature> features) {
            this.features = features;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Product build() {
            Assert.hasLength(this.name, "nome não pode estar em branco");
            Assert.hasLength(this.description, "descrição não pode estar em branco");
            Assert.isTrue(
                this.value != null && this.value.compareTo(new BigDecimal(0)) >= 0,
                "valor deve ser não nulo e positivo"
            );
            Assert.isTrue(this.quantity != null && this.quantity > 0, "quantidade deve ser não nula e positiva");
            Assert.notNull(this.category, "categoria é obrigatória");
            Assert.notEmpty(this.features, "característica é obrigatório");
            Assert.isTrue(this.features.size() >= 3, "deve ter no mínimo 3 características");
            Assert.notNull(this.user, "deve estar atrelado a um usuário");
            Product product = new Product();
            product.name = this.name;
            product.value = this.value;
            product.quantity = this.quantity;
            product.description = this.description;
            product.category = this.category;
            product.features = this.features;
            product.user = this.user;
            return product;
        }
    }
}
