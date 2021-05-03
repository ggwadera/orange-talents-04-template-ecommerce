package br.com.zupacademy.ggwadera.mercadolivre.product.question;

import java.time.LocalDateTime;

public class QuestionResponse {

  private final String title;
  private final LocalDateTime createdAt;

  public QuestionResponse(Question model) {
    this.title = model.getTitle();
    this.createdAt = model.getCreatedAt();
  }

  public String getTitle() {
    return title;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
