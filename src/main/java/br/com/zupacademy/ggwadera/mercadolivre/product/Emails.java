package br.com.zupacademy.ggwadera.mercadolivre.product;

import br.com.zupacademy.ggwadera.mercadolivre.product.question.Question;
import br.com.zupacademy.ggwadera.mercadolivre.util.email.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Emails {

  private final Mailer mailer;

  @Autowired
  public Emails(Mailer mailer) {
    this.mailer = mailer;
  }

  public void sendNewQuestionEmail(Question question) {
    mailer.send(
        String.format("<html>%s</html>", question.getTitle()),
        "Nova pergunta",
        "Mercado Livre",
        "pergunta@mercadolivre.com.br",
        question.getProductOwner().getEmail());
  }
}
