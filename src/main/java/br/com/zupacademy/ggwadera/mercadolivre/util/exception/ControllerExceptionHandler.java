package br.com.zupacademy.ggwadera.mercadolivre.util.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

  private final MessageSource messageSource;

  public ControllerExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public List<ErrorResponse> handleFieldErrors(MethodArgumentNotValidException exception) {
    return exception.getBindingResult().getFieldErrors().stream()
        .map(
            e ->
                new ErrorResponse(
                    e.getField(), messageSource.getMessage(e, LocaleContextHolder.getLocale())))
        .collect(Collectors.toList());
  }
}
