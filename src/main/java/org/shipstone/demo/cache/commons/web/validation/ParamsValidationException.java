package org.shipstone.demo.cache.commons.web.validation;

import org.shipstone.demo.cache.commons.web.exceptions.RegisteredException;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Projet commons Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
public class ParamsValidationException extends RegisteredException {

  private final ValidationError validationError;

  public ParamsValidationException(String code, List<FieldError> fieldErrorList) {
    this(code, null, fieldErrorList);
  }

  public ParamsValidationException(String code, String message, List<FieldError> fieldErrorList) {
    super(BAD_REQUEST, code, (message != null && message.trim().length() > 0) ? message : "Erreur de validation", null);
    this.validationError = new ValidationError(getMessage());
    fieldErrorList.forEach(fieldError -> {
      this.validationError.add(fieldError.getField(), fieldError.getDefaultMessage());
    });
  }

  private FieldInError fieldInError(FieldError fieldError) {
    return new FieldInError(fieldError.getField(), fieldError.getDefaultMessage());
  }

  public ValidationError getValidationError() {
    return validationError;
  }

}
