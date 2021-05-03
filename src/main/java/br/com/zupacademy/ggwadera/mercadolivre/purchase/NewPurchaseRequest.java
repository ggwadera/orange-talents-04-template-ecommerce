package br.com.zupacademy.ggwadera.mercadolivre.purchase;

import br.com.zupacademy.ggwadera.mercadolivre.product.Product;
import br.com.zupacademy.ggwadera.mercadolivre.purchase.payment.PaymentType;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;
import br.com.zupacademy.ggwadera.mercadolivre.util.validation.ExistsById;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.function.Function;

public class NewPurchaseRequest {

  @ExistsById(domainClass = Product.class)
  private final Long productId;

  @Positive @NotNull private final Integer quantity;

  @NotNull private final PaymentType paymentType;

  public NewPurchaseRequest(Long productId, Integer quantity, PaymentType paymentType) {
    this.productId = productId;
    this.quantity = quantity;
    this.paymentType = paymentType;
  }

  public Long getProductId() {
    return productId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public Purchase toModel(Function<Long, Product> findById, User user) {
    Product product = findById.apply(productId);
    return new Purchase(quantity, product, user, paymentType);
  }
}
