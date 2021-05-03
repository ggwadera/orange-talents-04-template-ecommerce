package br.com.zupacademy.ggwadera.mercadolivre.purchase.payment.gateway;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface PaymentGateway {

  URI getRedirectionURI(UUID paymentId);

  default String getReturnLink() {
    return ServletUriComponentsBuilder.fromCurrentContextPath()
        .replacePath("/pagamentos")
        .toUriString();
  }
}
