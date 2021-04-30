package br.com.zupacademy.ggwadera.mercadolivre.util.email;

public interface Mailer {

  /**
   * @param body HTML body
   * @param subject email subject
   * @param nameFrom sender display name
   * @param from sender email
   * @param to secipient email
   */
  void send(String body, String subject, String nameFrom, String from, String to);
}
