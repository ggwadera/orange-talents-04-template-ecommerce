package br.com.zupacademy.ggwadera.mercadolivre.product.question;

import br.com.zupacademy.ggwadera.mercadolivre.product.Product;
import br.com.zupacademy.ggwadera.mercadolivre.user.User;

import javax.validation.constraints.NotBlank;

public class NewQuestionRequest {

  @NotBlank private String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Question toModel(User user, Product product) {
    return new Question(title, user, product);
  }
}
