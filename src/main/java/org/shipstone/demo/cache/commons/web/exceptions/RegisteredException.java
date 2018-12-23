package org.shipstone.demo.cache.commons.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * Projet commons Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
public class RegisteredException extends Exception {

  private final HttpStatus httpStatus;

  private final String code;

  private String initialMessage;

  public RegisteredException(HttpStatus status, String code, String message) {
    this(status, code, message, null);
  }

  public RegisteredException(HttpStatus status, String code, String message, Exception e) {
    super(message, e);
    this.httpStatus = status;
    this.code = code;
    this.initialMessage = e.getMessage();
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getCode() {
    return code;
  }

  public String getInitialMessage() {
    return initialMessage;
  }
}
