package org.shipstone.demo.cache.commons.web.validation;

import org.springframework.validation.BindingResult;

public interface BindingResultProcessor {

  /**
   * Verifie que les paramètres d'un appel sont valide (Bean validation)
   *
   * @param bindingResult result de la validation du bean
   * @throws ParamsValidationException si un ou plus champs n'est pas valide, levé d'une exception
   */
  default void validateParams(BindingResult bindingResult) throws ParamsValidationException {
    if (bindingResult != null && bindingResult.getAllErrors() != null && !bindingResult.getAllErrors().isEmpty()) {
      throw new ParamsValidationException(getFieldValidationErrorCode(), bindingResult.getFieldErrors());
    }
  }

  String getFieldValidationErrorCode();

}
