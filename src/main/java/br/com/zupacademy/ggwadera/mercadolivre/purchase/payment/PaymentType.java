package br.com.zupacademy.ggwadera.mercadolivre.purchase.payment;

import br.com.zupacademy.ggwadera.mercadolivre.purchase.payment.gateway.PagseguroPayment;
import br.com.zupacademy.ggwadera.mercadolivre.purchase.payment.gateway.PaymentGateway;
import br.com.zupacademy.ggwadera.mercadolivre.purchase.payment.gateway.PaypalPayment;

public enum PaymentType {
  PAYPAL(new PaypalPayment()),
  PAGSEGURO(new PagseguroPayment());

  private final PaymentGateway paymentGateway;

  PaymentType(PaymentGateway paymentGateway) {
    this.paymentGateway = paymentGateway;
  }

  public PaymentGateway getPaymentGateway() {
    return paymentGateway;
  }
}
