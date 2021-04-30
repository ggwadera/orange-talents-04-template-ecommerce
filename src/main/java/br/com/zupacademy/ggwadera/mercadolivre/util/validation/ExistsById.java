package br.com.zupacademy.ggwadera.mercadolivre.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ExistsByIdValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsById {
  String message() default
      "não existe um(a) ${domainClass.getSimpleName()} com o id ${validatedValue}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  Class<?> domainClass();

  boolean optional() default false;
}
