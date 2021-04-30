package br.com.zupacademy.ggwadera.mercadolivre.product;

import br.com.zupacademy.ggwadera.mercadolivre.category.Category;
import br.com.zupacademy.ggwadera.mercadolivre.product.feature.NewProductFeatureRequest;
import br.com.zupacademy.ggwadera.mercadolivre.product.feature.ProductFeature;
import br.com.zupacademy.ggwadera.mercadolivre.product.opinion.ProductOpinion;
import br.com.zupacademy.ggwadera.mercadolivre.product.picture.ProductPicture;
import br.com.zupacademy.ggwadera.mercadolivre.product.question.Question;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Product {

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "product")
  private final Set<ProductPicture> pictures = new HashSet<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "product")
  private final Set<ProductOpinion> opinions = new HashSet<>();

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

  @CreationTimestamp
  @Column(nullable = false)
  private LocalDateTime createdAt;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "product")
  private Set<ProductFeature> features;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "product")
  private Set<Question> questions = new HashSet<>();

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private User owner;

  public boolean belongsTo(User user) {
    return this.owner.equals(user);
  }

  public void addPictures(Collection<String> picturesUri) {
    Set<ProductPicture> pictures =
        picturesUri.stream().map(uri -> new ProductPicture(uri, this)).collect(Collectors.toSet());
    this.pictures.addAll(pictures);
  }

  public User getOwner() {
    return this.owner;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return id.equals(product.getId()) && name.equals(product.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  public static final class Builder {
    private String name;
    private BigDecimal value;
    private Integer quantity;
    private String description;
    private Category category;
    private Collection<NewProductFeatureRequest> features;
    private User owner;

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

    public Builder withFeatures(Collection<NewProductFeatureRequest> features) {
      this.features = features;
      return this;
    }

    public Builder withUser(User user) {
      this.owner = user;
      return this;
    }

    public Product build() {
      Assert.hasLength(this.name, "nome não pode estar em branco");
      Assert.hasLength(this.description, "descrição não pode estar em branco");
      Assert.isTrue(
          this.value != null && this.value.compareTo(new BigDecimal(0)) >= 0,
          "valor deve ser não nulo e positivo");
      Assert.isTrue(
          this.quantity != null && this.quantity > 0, "quantidade deve ser não nula e positiva");
      Assert.notNull(this.category, "categoria é obrigatória");
      Assert.notEmpty(this.features, "característica é obrigatório");
      Assert.isTrue(this.features.size() >= 3, "deve ter no mínimo 3 características");
      Assert.notNull(this.owner, "deve estar atrelado a um usuário");
      Product product = new Product();
      product.name = this.name;
      product.value = this.value;
      product.quantity = this.quantity;
      product.description = this.description;
      product.category = this.category;
      product.owner = this.owner;
      product.features =
          this.features.stream().map(f -> f.toModel(product)).collect(Collectors.toSet());
      return product;
    }
  }
}
