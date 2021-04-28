package br.com.zupacademy.ggwadera.mercadolivre.product;

import br.com.zupacademy.ggwadera.mercadolivre.category.Category;
import br.com.zupacademy.ggwadera.mercadolivre.product.feature.NewProductFeatureRequest;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;
import br.com.zupacademy.ggwadera.mercadolivre.util.validation.ExistsById;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class NewProductRequest {

    @NotBlank
    private final String name;

    @Positive
    @NotNull
    private final BigDecimal value;

    @Positive
    @NotNull
    private final Integer quantity;

    @NotBlank
    @Size(max = 1000)
    private final String description;

    @NotNull
    @ExistsById(domainClass = Category.class)
    private final Long categoryId;

    @NotNull
    @Size(min = 3)
    private final Set<@Valid NewProductFeatureRequest> features;

    public NewProductRequest(String name, BigDecimal value, Integer quantity, String description, Long categoryId,
        Set<@Valid NewProductFeatureRequest> features) {
        this.name = name;
        this.value = value;
        this.quantity = quantity;
        this.description = description;
        this.categoryId = categoryId;
        this.features = features;
    }

    public Product toModel(EntityManager manager, User user) {
        Category category = manager.find(Category.class, categoryId);
        return new Product.Builder()
            .withName(name)
            .withValue(value)
            .withQuantity(quantity)
            .withDescription(description)
            .withFeatures(features.stream()
                .map(NewProductFeatureRequest::toModel)
                .collect(Collectors.toSet()))
            .withCategory(category)
            .withUser(user)
            .build();
    }
}
