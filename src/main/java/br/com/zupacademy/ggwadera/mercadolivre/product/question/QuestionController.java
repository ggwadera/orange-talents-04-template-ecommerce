package br.com.zupacademy.ggwadera.mercadolivre.product.question;

import br.com.zupacademy.ggwadera.mercadolivre.product.Product;
import br.com.zupacademy.ggwadera.mercadolivre.security.AuthenticatedUser;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;
import br.com.zupacademy.ggwadera.mercadolivre.util.email.Emails;
import br.com.zupacademy.ggwadera.mercadolivre.util.validation.ExistsById;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
@Transactional
@Validated
public class QuestionController {

  private final Emails emails;

  @PersistenceContext private EntityManager manager;

  public QuestionController(Emails emails) {
    this.emails = emails;
  }

  @PostMapping("/{id}/perguntas")
  public ResponseEntity<Void> addQuestion(
      @PathVariable @ExistsById(domainClass = Product.class) Long id,
      @RequestBody @Valid NewQuestionRequest request,
      @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
    User user = authenticatedUser.getUser();
    Product product = manager.find(Product.class, id);
    Question question = request.toModel(user, product);
    manager.persist(question);
    emails.sendNewQuestionEmail(question);
    return ResponseEntity.ok().build();
  }
}
