package org.shipstone.demo.cache.commons.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class RegisteredException extends Exception {

  private final HttpStatus httpStatus;

  private final String code;


  public RegisteredException(HttpStatus status, String code, String message) {
    this(status, code, message, null);
  }

  public RegisteredException(HttpStatus status, String code, String message, Exception e) {
    super(message, e);
    this.httpStatus = status;
    this.code = code;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getCode() {
    return code;
  }

}
