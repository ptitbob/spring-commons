package org.shipstone.demo.cache.commons.data;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Projet commons Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Followed<T> {

  @Column(name = "creator")
  @CreatedBy
  private T creator;

  @CreatedDate
  @Column(name = "created")
  private LocalDateTime created;

  @LastModifiedBy
  @Column(name = "modificator")
  private T modificator;

  @LastModifiedDate
  @Column(name = "modified")
  private LocalDateTime modified;

  public T getCreator() {
    return creator;
  }

  public void setCreator(T creator) {
    this.creator = creator;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public T getModificator() {
    return modificator;
  }

  public void setModificator(T modificator) {
    this.modificator = modificator;
  }

  public LocalDateTime getModified() {
    return modified;
  }

  public void setModified(LocalDateTime modified) {
    this.modified = modified;
  }
}
