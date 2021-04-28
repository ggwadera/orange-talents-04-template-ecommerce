package br.com.zupacademy.ggwadera.mercadolivre.product;

import br.com.zupacademy.ggwadera.mercadolivre.security.AuthenticatedUser;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<Void> newProduct(@RequestBody @Valid NewProductRequest request, @AuthenticationPrincipal
        AuthenticatedUser authenticatedUser) {
        User user = authenticatedUser.getUser();
        Product product = request.toModel(manager, user);
        manager.persist(product);
        return ResponseEntity.ok().build();
    }
}
