package br.com.zupacademy.ggwadera.mercadolivre.product.picture;

import br.com.zupacademy.ggwadera.mercadolivre.product.Product;
import br.com.zupacademy.ggwadera.mercadolivre.security.AuthenticatedUser;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;
import br.com.zupacademy.ggwadera.mercadolivre.util.validation.ExistsById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
@Transactional
@Validated
public class PictureController {

  private final Uploader uploader;

  @PersistenceContext private EntityManager manager;

  @Autowired
  public PictureController(Uploader uploader) {
    this.uploader = uploader;
  }

  @PostMapping("/{id}/imagens")
  public ResponseEntity<Void> addPictures(
      @PathVariable @ExistsById(domainClass = Product.class) Long id,
      @Valid NewPicturesRequest request,
      @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
    User user = authenticatedUser.getUser();
    Product product = manager.find(Product.class, id);
    if (!product.belongsTo(user)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Acesso negado");
    }
    product.addPictures(uploader.upload(request.getPictures()));
    manager.merge(product);
    return ResponseEntity.ok().build();
  }
}
