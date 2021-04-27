package br.com.zupacademy.ggwadera.mercadolivre.novacategoria;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/categorias")
@Transactional
public class NewCategoryController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    public ResponseEntity<Void> newCategory(@RequestBody @Valid NewCategoryRequest request) {
        Category category = request.toModel(manager);
        manager.persist(category);
        return ResponseEntity.ok().build();
    }
}
