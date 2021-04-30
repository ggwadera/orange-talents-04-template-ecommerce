package br.com.zupacademy.ggwadera.mercadolivre.product.opinion;

import br.com.zupacademy.ggwadera.mercadolivre.product.Product;
import br.com.zupacademy.ggwadera.mercadolivre.security.AuthenticatedUser;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;
import br.com.zupacademy.ggwadera.mercadolivre.util.validation.ExistsById;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
@Transactional
@Validated
public class OpinionController {

  @PersistenceContext private EntityManager manager;

  @PostMapping("/{id}/opinioes")
  public ResponseEntity<Void> addOpinion(
      @PathVariable @ExistsById(domainClass = Product.class) Long id,
      @RequestBody @Valid NewOpinionRequest request,
      @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
    User user = authenticatedUser.getUser();
    Product product = manager.find(Product.class, id);
    if (product.belongsTo(user)) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Não pode opinar sobre o próprio produto.");
    }
    manager.persist(request.toModel(user, product));
    return ResponseEntity.ok().build();
  }
}
