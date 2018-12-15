package org.shipstone.demo.cache.commons.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IdentifiedObject<T> {

  @JsonIgnore
  T getUniqueIdentifier();

}
