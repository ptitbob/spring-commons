package org.shipstone.demo.cache.commons.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Projet commons Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
public interface IdentifiedObject<T> {

  @JsonIgnore
  T getUniqueIdentifier();

}
