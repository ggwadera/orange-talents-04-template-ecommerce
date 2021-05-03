package br.com.zupacademy.ggwadera.mercadolivre.purchase.payment.gateway;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public class PagseguroPayment implements PaymentGateway {

  private static final String GATEWAY_URL = "https://pagseguro.com";
  private static final String PURCHASE_ID_PARAM = "returnId";
  private static final String REDIRECT_PARAM = "redirectUrl";

  @Override
  public URI getRedirectionURI(UUID paymentId) {
    return UriComponentsBuilder.fromHttpUrl(GATEWAY_URL)
        .queryParam(PURCHASE_ID_PARAM, paymentId)
        .queryParam(REDIRECT_PARAM, getReturnLink())
        .build()
        .toUri();
  }
}
