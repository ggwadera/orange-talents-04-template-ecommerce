package br.com.zupacademy.ggwadera.mercadolivre.product;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class DistinctFeatureNameValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return NewProductRequest.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    if (errors.hasErrors()) return;
    final NewProductRequest request = (NewProductRequest) target;
    final Set<String> duplicated = request.findDuplicatedFeatures();
    if (!duplicated.isEmpty()) {
      errors.rejectValue("features", null, "Características com nomes iguais: " + duplicated);
    }
  }
}
