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
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

  @CreationTimestamp
  @Column(nullable = false)
  private LocalDateTime createdAt;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "product")
  private final Set<ProductPicture> pictures = new HashSet<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "product")
  private final Set<ProductOpinion> opinions = new HashSet<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "product")
  private Set<ProductFeature> features = new HashSet<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "product")
  private Set<Question> questions = new HashSet<>();

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private User owner;

  public Set<ProductPicture> getPictures() {
    return pictures;
  }

  public Set<ProductOpinion> getOpinions() {
    return opinions;
  }

  public BigDecimal getValue() {
    return value;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public String getDescription() {
    return description;
  }

  public Set<ProductFeature> getFeatures() {
    return features;
  }

  public Set<Question> getQuestions() {
    return questions;
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

  public Category getCategory() {
    return category;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public boolean belongsTo(User user) {
    return this.owner.equals(user);
  }

  public void addPictures(Collection<String> picturesUri) {
    Set<ProductPicture> pictures =
        picturesUri.stream().map(uri -> new ProductPicture(uri, this)).collect(Collectors.toSet());
    this.pictures.addAll(pictures);
  }

  public Double getAverageRating() {
    OptionalDouble average =
        this.opinions.stream().mapToInt(ProductOpinion::getRating).asDoubleStream().average();
    if (average.isEmpty()) return null;
    return average.getAsDouble();
  }

  public int getTotalRatings() {
    return this.opinions.size();
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

  public boolean reduceInventory(@Positive Integer quantity) {
    Assert.isTrue(quantity > 0, "Quantidade deve ser positiva.");
    if (this.quantity < quantity) return false;
    this.quantity -= quantity;
    return true;
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
