package br.com.zupacademy.ggwadera.mercadolivre.product.opinion;

public class OpinionResponse {

  private final Integer rating;
  private final String title;
  private final String description;

  public OpinionResponse(ProductOpinion model) {
    this.rating = model.getRating();
    this.title = model.getTitle();
    this.description = model.getDescription();
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
}
