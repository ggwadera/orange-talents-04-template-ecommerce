package br.com.zupacademy.ggwadera.mercadolivre.product.feature;

public class FeatureResponse {

  private final String name;
  private final String description;

  public FeatureResponse(ProductFeature model) {
    this.name = model.getName();
    this.description = model.getDescription();
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }
}
