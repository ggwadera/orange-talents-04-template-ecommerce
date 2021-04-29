package br.com.zupacademy.ggwadera.mercadolivre.product;

import br.com.zupacademy.ggwadera.mercadolivre.product.picture.NewPicturesRequest;
import br.com.zupacademy.ggwadera.mercadolivre.product.picture.Uploader;
import br.com.zupacademy.ggwadera.mercadolivre.security.AuthenticatedUser;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
@Transactional
public class ProductController {

    private final Uploader uploader;

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    public ProductController(Uploader uploader) {this.uploader = uploader;}

    @InitBinder(value = "newProduct")
    public void init(WebDataBinder binder) {
        binder.addValidators(new DistinctFeatureNameValidator());
    }

    @PostMapping
    public ResponseEntity<Void> newProduct(@RequestBody @Valid NewProductRequest request,
        @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        User user = authenticatedUser.getUser();
        Product product = request.toModel(manager, user);
        manager.persist(product);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/imagens")
    public ResponseEntity<Void> addPictures(@PathVariable Long id, @Valid NewPicturesRequest request,
        @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        User user = authenticatedUser.getUser();
        Product product = Optional.ofNullable(manager.find(Product.class, id))
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Não existe um produto com a id " + id
            ));
        if (!product.belongsTo(user)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Acesso negado");
        }
        product.addPictures(uploader.upload(request.getPictures()));
        manager.merge(product);
        return ResponseEntity.ok().build();
    }
}
