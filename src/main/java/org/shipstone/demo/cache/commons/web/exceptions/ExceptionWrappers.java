package org.shipstone.demo.cache.commons.web.exceptions;

import org.shipstone.demo.cache.commons.web.validation.ConsistencyEndpointException;
import org.shipstone.demo.cache.commons.web.validation.ParamsValidationException;
import org.shipstone.demo.cache.commons.web.validation.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.charset.Charset;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

/**
 * Projet commons Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
public abstract class ExceptionWrappers {

  private static final String HEADER_ERROR_CODE = "x-error-code";

  private static final String HEADER_ERROR_MESSAGE = "x-error-message";

  protected HttpHeaders httpHeaders(RegisteredException e) {
    return httpHeaders(e.getCode(), e.getMessage());
  }

  protected HttpHeaders httpHeaders(RegisteredException e, MediaType mediaType) {
    return httpHeaders(e.getCode(), e.getMessage(), mediaType);
  }

  protected HttpHeaders httpHeaders(String code, String message) {
    return httpHeaders(code, message, TEXT_PLAIN);
  }

  protected HttpHeaders httpHeaders(String code, String message, MediaType mediaType) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(HEADER_ERROR_CODE, code);
    httpHeaders.add(HEADER_ERROR_MESSAGE, message);
    httpHeaders.setContentType(mediaType);
    return httpHeaders;
  }

  protected ResponseEntity<String> responseEntity(RegisteredException e) {
    final HttpStatus httpStatus = e.getHttpStatus() == null ? INTERNAL_SERVER_ERROR : e.getHttpStatus();
    return responseEntity(e, httpStatus, httpHeaders(e));
  }

  protected ResponseEntity<String> responseEntity(RegisteredException e, HttpStatus httpStatus) {
    return responseEntity(e, httpStatus, httpHeaders(e));
  }

  protected ResponseEntity<String> responseEntity(RegisteredException e, HttpStatus httpStatus, HttpHeaders httpHeaders) {
    httpHeaders.setContentType(new MediaType("text", "plain", Charset.forName("UTF-8")));
    return new ResponseEntity<>(e.getMessage(), httpHeaders, httpStatus);
  }

  @ExceptionHandler(ParamsValidationException.class)
  public ResponseEntity<ValidationError> paramsValidationException(
      ParamsValidationException e
  ) {
    HttpHeaders httpHeaders = httpHeaders(e.getCode(), e.getMessage(), APPLICATION_JSON);
    return new ResponseEntity<>(e.getValidationError(), httpHeaders, e.getHttpStatus());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> entityNotFoundException(
      EntityNotFoundException e
  ) {
    return responseEntity(e);
  }

  @ExceptionHandler(EntityAlreadyExistException.class)
  public ResponseEntity<String> entityAlreadyExistException(
      EntityAlreadyExistException e
  ) {
    return responseEntity(e);
  }

  @ExceptionHandler(ConsistencyEndpointException.class)
  public ResponseEntity<String> consistencyEndpointException(
      ConsistencyEndpointException e
  ) {
    return responseEntity(e);
  }

}
