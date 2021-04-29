package br.com.zupacademy.ggwadera.mercadolivre.product.opinion;

import br.com.zupacademy.ggwadera.mercadolivre.product.Product;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewOpinionRequest {

    @Min(1)
    @Max(5)
    @NotNull
    private final Integer rating;

    @NotBlank
    @Length(max = 255)
    private final String title;

    @NotBlank
    @Length(max = 500)
    private final String description;

    public NewOpinionRequest(Integer rating, String title, String description) {
        this.rating = rating;
        this.title = title;
        this.description = description;
    }

    public Integer getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ProductOpinion toModel(User user, Product product) {
        return new ProductOpinion(rating, title, description, user, product);
    }
}
