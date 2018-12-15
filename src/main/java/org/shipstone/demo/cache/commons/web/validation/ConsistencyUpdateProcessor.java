package org.shipstone.demo.cache.commons.web.validation;

import org.shipstone.demo.cache.commons.domain.IdentifiedObject;
import org.springframework.validation.BindingResult;

/**
 * Projet commons Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
public interface ConsistencyUpdateProcessor extends BindingResultProcessor {

  default <D extends IdentifiedObject> void validateConsistecy(String ResourceId, D data, BindingResult bindingResult) throws ParamsValidationException, ConsistencyEndpointException {
    validateUniqueIdentifier(ResourceId, data);
    validateParams(bindingResult);
  }

  default <D extends IdentifiedObject> void validateUniqueIdentifier(String ResourceId, D data) throws ConsistencyEndpointException {
    if (!ResourceId.equals(data.getUniqueIdentifier())) {
      throw new ConsistencyEndpointException();
    }
  }

}
