package org.shipstone.demo.cache.commons.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.ALL;

/**
 * Projet commons Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
public interface ResponseEntityProcessor {

  default <D> ResponseEntity<List<D>> responseEntity(List<D> list, Pageable pageable) {
    return responseEntity(page(list, pageable));
  }

  default <E, D> ResponseEntity<List<D>> responseEntity(Page<E> page, Function<E, D> function) {
    List<D> list = page.getContent().stream()
        .map(function)
        .collect(Collectors.toList());
    HttpHeaders httpHeaders = new HttpHeaders();
    HttpStatus httpStatus = processHeaderAndStatus(page, httpHeaders);
    return new ResponseEntity<>(list, httpHeaders, httpStatus);
  }

  default <D> ResponseEntity<List<D>> responseEntity(Page<D> page) {
    HttpHeaders httpHeaders = new HttpHeaders();
    HttpStatus httpStatus = processHeaderAndStatus(page, httpHeaders);
    return new ResponseEntity<>(page.getContent(), httpHeaders, httpStatus);
  }

  private <E> HttpStatus processHeaderAndStatus(Page<E> page, HttpHeaders httpHeaders) {
    httpHeaders.add("x-total-element", String.valueOf(page.getTotalElements()));
    httpHeaders.add("x-total-page", String.valueOf(page.getTotalPages()));
    httpHeaders.add("x-current-page", String.valueOf(page.getPageable().getPageNumber()));
    httpHeaders.add("x-page-size", String.valueOf(page.getPageable().getPageSize()));
    HttpStatus httpStatus = OK;
    if (page.getTotalElements() > page.getPageable().getPageSize()) {
      httpStatus = PARTIAL_CONTENT;
    } else if (page.getTotalElements() == 0) {
      httpStatus = NO_CONTENT;
    }
    return httpStatus;
  }

  default <D> Page<D> page(List<D> list, Pageable pageable) {
    if (list == null || list.isEmpty()) {
      return new PageImpl<>(emptyList(), pageable, 0);
    }
    if (list.size() < pageable.getOffset()) {
      return new PageImpl<>(list, pageable, list.size());
    }
    if (list.size() < (pageable.getOffset() + pageable.getPageSize())) {
      return new PageImpl<>(list.subList(Long.valueOf(pageable.getOffset()).intValue(), list.size()), pageable, list.size());
    } else {
      return new PageImpl<>(list.subList(Long.valueOf(pageable.getOffset()).intValue(), Long.valueOf(pageable.getOffset() + pageable.getPageSize()).intValue()), pageable, list.size());
    }
  }

  default <D> ResponseEntity<D> responseEntity(D data, HttpHeaders requestHttpHeaders) {
    return responseEntity(data, requestHttpHeaders, null);
  }

  default <D> ResponseEntity<D> responseEntity(D data, HttpHeaders requestHttpHeaders, URI location) {
    HttpHeaders httpHeaders = new HttpHeaders();
    HttpStatus httpStatus = OK;
    if (location != null) {
      httpStatus = CREATED;
      httpHeaders.setLocation(location);
    }
    if (requestHttpHeaders.getAccept().size() == 0 || requestHttpHeaders.getAccept().contains(ALL)) {
      return new ResponseEntity<>(httpHeaders, httpStatus);
    }
    return new ResponseEntity<>(data, httpHeaders, httpStatus);
  }

}