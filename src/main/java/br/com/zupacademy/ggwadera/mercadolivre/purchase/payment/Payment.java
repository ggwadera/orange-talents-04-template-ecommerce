package br.com.zupacademy.ggwadera.mercadolivre.purchase.payment;

import br.com.zupacademy.ggwadera.mercadolivre.purchase.Purchase;

import javax.persistence.*;
import java.net.URI;
import java.util.UUID;

@Entity
public class Payment {

  @Id @GeneratedValue private UUID uuid;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentType paymentType;

  @OneToOne(optional = false, cascade = CascadeType.ALL)
  private Purchase purchase;

  @Deprecated
  public Payment() {}

  public Payment(PaymentType paymentType, Purchase purchase) {
    this.paymentType = paymentType;
    this.purchase = purchase;
  }

  public UUID getUuid() {
    return uuid;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public Purchase getPurchase() {
    return purchase;
  }

  public URI getPaymentLink() {
    return paymentType.getPaymentGateway().getRedirectionURI(uuid);
  }
}
