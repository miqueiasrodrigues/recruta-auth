package br.com.miqueias.recruta_auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CpfAlreadyInUseException extends RuntimeException{
  private static final long serialVersionUID = 1L;
  public CpfAlreadyInUseException(String message) {
    super(message);
  }
}