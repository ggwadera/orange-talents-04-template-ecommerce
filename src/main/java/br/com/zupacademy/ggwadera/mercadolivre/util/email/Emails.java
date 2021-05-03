package br.com.zupacademy.ggwadera.mercadolivre.util.email;

import br.com.zupacademy.ggwadera.mercadolivre.product.question.Question;
import br.com.zupacademy.ggwadera.mercadolivre.purchase.Purchase;
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

  public void sendNewPurchaseEmail(Purchase purchase) {
    mailer.send(
        String.format("<html>Nova venda do seu produto %s</html>", purchase.getProduct().getName()),
        "Nova Venda",
        "Mercado Livre",
        "vendas@mercadolivre.com.br",
        purchase.getProductOwner().getEmail());
  }
}
