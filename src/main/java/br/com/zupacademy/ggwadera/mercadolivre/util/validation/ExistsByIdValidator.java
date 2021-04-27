package br.com.zupacademy.ggwadera.mercadolivre.util.validation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistsByIdValidator implements ConstraintValidator<ExistsById, Object> {

    @PersistenceContext
    private EntityManager manager;

    private Class<?> klass;
    private boolean optional;

    @Override
    public void initialize(ExistsById params) {
        klass = params.domainClass();
        optional = params.optional();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (optional && value == null) return true;
        return !manager.createQuery("select 1 from " + klass.getName() + " where id = :value")
            .setParameter("value", value)
            .getResultList()
            .isEmpty();
    }

}
