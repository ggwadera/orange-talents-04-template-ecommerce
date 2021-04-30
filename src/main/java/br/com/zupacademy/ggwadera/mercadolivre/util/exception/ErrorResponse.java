package br.com.zupacademy.ggwadera.mercadolivre.util.exception;

public class ErrorResponse {

  private final String campo;
  private final String erro;

  public ErrorResponse(String campo, String erro) {
    this.campo = campo;
    this.erro = erro;
  }

  public String getCampo() {
    return campo;
  }

  public String getErro() {
    return erro;
  }
}
