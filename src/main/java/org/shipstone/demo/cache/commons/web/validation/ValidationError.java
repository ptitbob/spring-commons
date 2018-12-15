package org.shipstone.demo.cache.commons.web.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Projet commons Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
public class ValidationError implements Serializable {

  private static final long serialVersionUID = -5700141478117210848L;

  private final String message;

  private final List<FieldInError> fieldInErrorList;

  public ValidationError(String message) {
    this.message = message;
    fieldInErrorList = new ArrayList<>();
  }

  public String getMessage() {
    return message;
  }

  public List<FieldInError> getFieldInErrorList() {
    return fieldInErrorList;
  }

  public void add(final String field, String defaultMessage) {
    FieldInError fieldInError = fieldInErrorList.stream().filter(fieldInErrorInList -> field.equals(fieldInErrorInList.getField())).findFirst().orElse(null);
    if (fieldInError == null) {
      fieldInError = new FieldInError(field);
      fieldInErrorList.add(fieldInError);
    }
    fieldInError.add(defaultMessage);
  }
}
