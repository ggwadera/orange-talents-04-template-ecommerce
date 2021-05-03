package br.com.zupacademy.ggwadera.mercadolivre.purchase;

import br.com.zupacademy.ggwadera.mercadolivre.product.Product;
import br.com.zupacademy.ggwadera.mercadolivre.purchase.payment.Payment;
import br.com.zupacademy.ggwadera.mercadolivre.purchase.payment.PaymentType;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Purchase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false)
  private BigDecimal value;

  @ManyToOne(optional = false)
  private Product product;

  @ManyToOne(optional = false)
  private User purchaser;

  @OneToOne(optional = false, mappedBy = "purchase", cascade = CascadeType.ALL)
  private Payment payment;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PurchaseStatus status;

  @CreationTimestamp
  @Column(nullable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Deprecated
  public Purchase() {}

  public Purchase(Integer quantity, Product product, User purchaser, PaymentType paymentType) {
    this.quantity = quantity;
    this.product = product;
    this.purchaser = purchaser;
    this.payment = new Payment(paymentType, this);
    this.status = PurchaseStatus.STARTED;
    this.value = product.getValue();
  }

  public Long getId() {
    return id;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public BigDecimal getValue() {
    return value;
  }

  public Product getProduct() {
    return product;
  }

  public User getPurchaser() {
    return purchaser;
  }

  public Payment getPayment() {
    return payment;
  }

  public PurchaseStatus getStatus() {
    return status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public User getProductOwner() {
    return product.getOwner();
  }
}
