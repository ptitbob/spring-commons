package org.shipstone.demo.cache.commons.web.validation;

import org.shipstone.demo.cache.commons.web.exceptions.RegisteredException;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Projet commons Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
public class ConsistencyEndpointException extends RegisteredException {
  public ConsistencyEndpointException() {
    super(BAD_REQUEST, "400-0000", "Incohérence entre le path et l'identifiant unique", null);
  }
}
