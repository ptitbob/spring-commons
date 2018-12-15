package org.shipstone.demo.cache.commons.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.shipstone.demo.cache.commons.web.Views;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.time.LocalDateTime;

import static org.shipstone.demo.cache.commons.Constants.JSON_TIMESTAMP_PATTERN;

/**
 * Projet commons Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
public abstract class FollowedDto {

  @JsonView(Views.Details.class)
  private String creator;

  @JsonView(Views.Details.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JSON_TIMESTAMP_PATTERN)
  private LocalDateTime created;

  @JsonView(Views.Details.class)
  private String modificator;

  @JsonView(Views.Details.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = JSON_TIMESTAMP_PATTERN)
  private LocalDateTime modified;

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public String getModificator() {
    return modificator;
  }

  public void setModificator(String modificator) {
    this.modificator = modificator;
  }

  public LocalDateTime getModified() {
    return modified;
  }

  public void setModified(LocalDateTime modified) {
    this.modified = modified;
  }
}
