package org.shipstone.demo.cache.commons.web.validation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class FieldInError implements Serializable {

  private static final long serialVersionUID = -6543014484634497110L;

  private final String field;

  private final List<String> messages;

  public FieldInError(String field, String message) {
    this(field);
    this.messages.add(message);
  }

  public FieldInError(String field) {
    this.field = field;
    this.messages = new ArrayList<>();
  }

  public String getField() {
    return field;
  }

  public List<String> getMessages() {
    return messages;
  }

  public void add(String message) {
    this.messages.add(message);
  }
}
