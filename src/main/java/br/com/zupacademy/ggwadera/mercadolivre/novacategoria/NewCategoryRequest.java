package br.com.zupacademy.ggwadera.mercadolivre.novacategoria;

import br.com.zupacademy.ggwadera.mercadolivre.util.validation.ExistsById;
import br.com.zupacademy.ggwadera.mercadolivre.util.validation.UniqueValue;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class NewCategoryRequest {

    @NotBlank
    @UniqueValue(domainClass = Category.class, fieldName = "name")
    private String name;

    @Positive
    @ExistsById(domainClass = Category.class, optional = true)
    private Long parentCategoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Category toModel(EntityManager manager) {
        if (parentCategoryId == null) return new Category(name);
        Category parent = manager.find(Category.class, parentCategoryId);
        return new Category(name, parent);
    }

}
