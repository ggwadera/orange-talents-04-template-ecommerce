package br.com.zupacademy.ggwadera.mercadolivre.purchase;

import br.com.zupacademy.ggwadera.mercadolivre.product.Product;
import br.com.zupacademy.ggwadera.mercadolivre.product.ProductRepository;
import br.com.zupacademy.ggwadera.mercadolivre.purchase.payment.Payment;
import br.com.zupacademy.ggwadera.mercadolivre.security.AuthenticatedUser;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;
import br.com.zupacademy.ggwadera.mercadolivre.util.email.Emails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/compras")
@Transactional
public class PurchaseController {

  private final ProductRepository productRepo;
  private final PurchaseRepository purchaseRepo;
  private final Emails emails;

  @Autowired
  public PurchaseController(
      ProductRepository productRepo, PurchaseRepository purchaseRepo, Emails emails) {
    this.productRepo = productRepo;
    this.purchaseRepo = purchaseRepo;
    this.emails = emails;
  }

  @PostMapping
  public ResponseEntity<String> newPurchase(
      @RequestBody NewPurchaseRequest request,
      @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
    User purchaser = authenticatedUser.getUser();
    Purchase purchase = request.toModel(productRepo::getOne, purchaser);
    Product product = purchase.getProduct();
    if (!product.reduceInventory(purchase.getQuantity())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente.");
    }
    purchase = purchaseRepo.save(purchase);
    productRepo.save(product);
    emails.sendNewPurchaseEmail(purchase);
    Payment payment = purchase.getPayment();
    return ResponseEntity.status(HttpStatus.FOUND).location(payment.getPaymentLink()).build();
  }
}
