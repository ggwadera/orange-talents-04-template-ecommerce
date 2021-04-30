package br.com.zupacademy.ggwadera.mercadolivre.product.opinion;

import br.com.zupacademy.ggwadera.mercadolivre.product.Product;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}))
public class ProductOpinion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, columnDefinition = "tinyint")
  private Integer rating;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, columnDefinition = "text")
  private String description;

  @ManyToOne(optional = false)
  private User user;

  @ManyToOne(optional = false)
  private Product product;

  @Deprecated
  public ProductOpinion() {}

  public ProductOpinion(
      Integer rating, String title, String description, User user, Product product) {
    this.rating = rating;
    this.title = title;
    this.description = description;
    this.user = user;
    this.product = product;
  }

  public Long getId() {
    return id;
  }

  public Integer getRating() {
    return rating;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public User getUser() {
    return user;
  }

  public Product getProduct() {
    return product;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductOpinion that = (ProductOpinion) o;
    return user.equals(that.getUser()) && product.equals(that.getProduct());
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, product);
  }
}
