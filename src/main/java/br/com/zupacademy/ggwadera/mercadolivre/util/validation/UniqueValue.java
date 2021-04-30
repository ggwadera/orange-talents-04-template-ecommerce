package br.com.zupacademy.ggwadera.mercadolivre.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {UniqueValueValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueValue {

  String message() default
      "já existe um(a) ${domainClass.getSimpleName()} com o valor ${validatedValue}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String fieldName();

  Class<?> domainClass();
}
