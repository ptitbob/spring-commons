package org.shipstone.demo.cache.commons.web.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Projet commons Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
public class EntityNotFoundException extends RegisteredException {

  public EntityNotFoundException(String entity, String key, String code) {
    super(NOT_FOUND, code, String.format("%s non trouvé(e) - clé : %s", entity, key));
  }

}
