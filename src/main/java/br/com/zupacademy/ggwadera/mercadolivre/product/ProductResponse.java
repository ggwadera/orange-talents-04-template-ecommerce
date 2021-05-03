package br.com.zupacademy.ggwadera.mercadolivre.product;

import br.com.zupacademy.ggwadera.mercadolivre.product.feature.FeatureResponse;
import br.com.zupacademy.ggwadera.mercadolivre.product.opinion.OpinionResponse;
import br.com.zupacademy.ggwadera.mercadolivre.product.picture.ProductPicture;
import br.com.zupacademy.ggwadera.mercadolivre.product.question.QuestionResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

  private final String name;
  private final BigDecimal value;
  private final Integer quantity;
  private final String description;
  private final List<String> pictures;
  private final List<FeatureResponse> features;
  private final List<OpinionResponse> opinions;
  private final List<QuestionResponse> questions;
  private final Double averageRating;
  private final Integer totalRatings;
  private final LocalDateTime createdAt;

  public ProductResponse(Product model) {
    this.name = model.getName();
    this.value = model.getValue();
    this.quantity = model.getQuantity();
    this.description = model.getDescription();
    this.pictures =
        model.getPictures().stream().map(ProductPicture::getUri).collect(Collectors.toList());
    this.features =
        model.getFeatures().stream().map(FeatureResponse::new).collect(Collectors.toList());
    this.opinions =
        model.getOpinions().stream().map(OpinionResponse::new).collect(Collectors.toList());
    this.questions =
        model.getQuestions().stream().map(QuestionResponse::new).collect(Collectors.toList());
    this.averageRating = model.getAverageRating();
    this.totalRatings = model.getTotalRatings();
    this.createdAt = model.getCreatedAt();
  }

  public String getName() {
    return name;
  }

  public BigDecimal getValue() {
    return value;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public String getDescription() {
    return description;
  }

  public List<String> getPictures() {
    return pictures;
  }

  public List<FeatureResponse> getFeatures() {
    return features;
  }

  public List<OpinionResponse> getOpinions() {
    return opinions;
  }

  public List<QuestionResponse> getQuestions() {
    return questions;
  }

  public Double getAverageRating() {
    return averageRating;
  }

  public Integer getTotalRatings() {
    return totalRatings;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
