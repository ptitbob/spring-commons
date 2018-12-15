package org.shipstone.demo.cache.commons.web;

import org.shipstone.demo.cache.commons.domain.IdentifiedObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Projet commons Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
public interface ResponseIdentifiedObjectProcessor extends ResponseEntityProcessor {

  default <D extends IdentifiedObject> ResponseEntity<D> responseEntity(D data, HttpHeaders requestHttpHeaders, UriComponentsBuilder uriComponentsBuilder) {
    return responseEntity(data, requestHttpHeaders, uriComponentsBuilder
        .path("/" + getBasePath() + "/" + data.getUniqueIdentifier())
        .build()
        .toUri());
  }

  String getBasePath();

}
