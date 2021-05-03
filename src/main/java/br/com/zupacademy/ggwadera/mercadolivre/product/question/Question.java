package br.com.zupacademy.ggwadera.mercadolivre.product.question;

import br.com.zupacademy.ggwadera.mercadolivre.product.Product;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}))
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @CreationTimestamp
  @Column(nullable = false)
  private LocalDateTime createdAt;

  @ManyToOne(optional = false)
  private User user;

  @ManyToOne(optional = false)
  private Product product;

  @Deprecated
  public Question() {}

  public Question(String title, User user, Product product) {
    this.title = title;
    this.user = user;
    this.product = product;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public User getUser() {
    return user;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Product getProduct() {
    return product;
  }

  public User getProductOwner() {
    return product.getOwner();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Question question = (Question) o;
    return getUser().equals(question.getUser()) && getProduct().equals(question.getProduct());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUser(), getProduct());
  }
}
