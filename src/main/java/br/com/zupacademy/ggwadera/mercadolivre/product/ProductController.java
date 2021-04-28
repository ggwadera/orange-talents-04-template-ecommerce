package br.com.zupacademy.ggwadera.mercadolivre.product;

import br.com.zupacademy.ggwadera.mercadolivre.security.AuthenticatedUser;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
@Transactional
public class ProductController {

    @PersistenceContext
    private EntityManager manager;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(new DistinctFeatureNameValidator());
    }

    @PostMapping
    public ResponseEntity<Void> newProduct(@RequestBody @Valid NewProductRequest request, @AuthenticationPrincipal
        AuthenticatedUser authenticatedUser) {
        User user = authenticatedUser.getUser();
        Product product = request.toModel(manager, user);
        manager.persist(product);
        return ResponseEntity.ok().build();
    }
}
