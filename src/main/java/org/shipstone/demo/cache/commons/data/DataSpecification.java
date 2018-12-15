package org.shipstone.demo.cache.commons.data;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.shipstone.demo.cache.commons.data.DataSpecification.ValueOperation.*;

/**
 * Projet commons Spring
 *
 * @author François Robert
 * LICENCE Apache 2.0
 */
public class DataSpecification<T> implements Specification<T> {

  private final Map<String, Join> joinTableMap;

  private final List<ValueFieldCondition> valueFieldConditionList;

  private String joker;

  /**
   * constructeur
   */
  public DataSpecification() {
    this.valueFieldConditionList = new ArrayList<>();
    this.joinTableMap = new HashMap<>();
  }

  public DataSpecification(String joker) {
    this();
    this.joker = joker.trim();
  }

  public DataSpecification<T> condition(String joinTable, String field, ValueOperation operation, Object value) {
    if (value != null) {
      if (operation == null) {
        operation = EQUAL;
        if (joker != null && joker.length() != 0 && String.class.isAssignableFrom(value.getClass())) {
          String valueAsString = (String) value;
          if (valueAsString.lastIndexOf(joker) == valueAsString.length() - 1) {
            operation = START_WITH_IGNORECASE;
            valueAsString = valueAsString.substring(0, valueAsString.lastIndexOf(joker));
          }
          if (valueAsString.indexOf(joker) == 0) {
            valueAsString = valueAsString.substring(1);
            if (START_WITH_IGNORECASE.equals(operation)) {
              operation = LIKE_IGNORECASE;
            } else {
              operation = END_WITH_IGNORECASE;
            }
            if (valueAsString.contains(joker)) {
              if ("*".equals(joker)) {
                // regex token to avoid
                valueAsString = valueAsString.replaceAll("\\*", "%");
              } else {
                valueAsString = valueAsString.replaceAll(joker, "%");
              }
              operation = LIKE_IGNORECASE;
            }
          }
          value = valueAsString;
        }
      }
      valueFieldConditionList.add(new ValueFieldCondition(joinTable, field, operation, value));
    }
    return this;
  }

  public DataSpecification<T> condition(String field, ValueOperation operation, Object value) {
    return condition(null, field, operation, value);
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
    if (!valueFieldConditionList.isEmpty()) {
      List<ValueFieldCondition> valueFieldConditionListInternal = new ArrayList<>(this.valueFieldConditionList);
      Predicate predicate = getPredicateFrom(valueFieldConditionListInternal.remove(0), criteriaBuilder, root);
      for (ValueFieldCondition nextValueFieldCondition : valueFieldConditionListInternal) {
        predicate = criteriaBuilder.and(predicate, getPredicateFrom(nextValueFieldCondition, criteriaBuilder, root));
      }
      return predicate;
    }
    return null;
  }

  private Predicate getPredicateFrom(ValueFieldCondition valueFieldCondition, CriteriaBuilder criteriaBuilder, Root<T> root) {
    switch (valueFieldCondition.operation) {
      case EQUAL:
        return criteriaBuilder.equal(getPath(valueFieldCondition, root), valueFieldCondition.value);
      case EQUAL_IGNORECASE:
        return criteriaBuilder.equal(criteriaBuilder.upper(getPath(valueFieldCondition, root)), String.valueOf(valueFieldCondition.value).toUpperCase());
      case START_WITH:
      case START_WITH_IGNORECASE:
      case LIKE_IGNORECASE:
      case LIKE:
      case END_WITH:
      case END_WITH_IGNORECASE:
        return getPredicateLike(valueFieldCondition, criteriaBuilder, root);
      case IN:
        return criteriaBuilder.in(getPath(valueFieldCondition, root)).value(valueFieldCondition.value);
      default:
        throw new NotYetImplementedException("pas de chance ... Do it yourself");
    }
  }

  private <Y> Path<Y> getPath(ValueFieldCondition valueFieldCondition, Root<T> root) {
    return valueFieldCondition.joinTable == null ? root.get(valueFieldCondition.field) : getJoin(valueFieldCondition, root).get(valueFieldCondition.field);
  }

  private Join getJoin(ValueFieldCondition valueFieldCondition, Root<T> root) {
    Join join;
    if (this.joinTableMap.containsKey(valueFieldCondition.joinTable)) {
      join = this.joinTableMap.get(valueFieldCondition.joinTable);
    } else {
      join = root.join(valueFieldCondition.joinTable, JoinType.LEFT);
      this.joinTableMap.put(valueFieldCondition.joinTable, join);
    }
    return join;
  }

  private Predicate getPredicateLike(ValueFieldCondition valueFieldCondition, CriteriaBuilder criteriaBuilder, Root<T> root) {
    if (String.class.isAssignableFrom(valueFieldCondition.value.getClass())) {
      Predicate predicate;
      String valueToTest = (LIKE_IGNORECASE.equals(valueFieldCondition.operation) || START_WITH_IGNORECASE.equals(valueFieldCondition.operation) || END_WITH_IGNORECASE.equals(valueFieldCondition.operation)) ? String.valueOf(valueFieldCondition.value).toUpperCase() : String.valueOf(valueFieldCondition.value);
      switch (valueFieldCondition.operation) {
        case LIKE_IGNORECASE:
          predicate = criteriaBuilder.like(criteriaBuilder.upper(getPath(valueFieldCondition, root)), "%" + valueToTest + "%");
          break;
        case LIKE:
          predicate = criteriaBuilder.like(getPath(valueFieldCondition, root), "%" + valueToTest + "%");
          break;
        case START_WITH:
          predicate = criteriaBuilder.like(getPath(valueFieldCondition, root), valueToTest + "%");
          break;
        case START_WITH_IGNORECASE:
          predicate = criteriaBuilder.like(criteriaBuilder.upper(getPath(valueFieldCondition, root)), valueToTest + "%");
          break;
        case END_WITH:
          predicate = criteriaBuilder.like(getPath(valueFieldCondition, root), "%" + valueToTest);
          break;
        case END_WITH_IGNORECASE:
          predicate = criteriaBuilder.like(criteriaBuilder.upper(getPath(valueFieldCondition, root)), "%" + valueToTest);
          break;
        default:
          throw new IllegalArgumentException("L'opération like ne peut être réalisé");
      }
      return predicate;
    } else {
      throw new IllegalArgumentException("L'opération like ne peut se faire que sur une chaine");
    }
  }

  private class ValueFieldCondition {
    private final String joinTable;
    private final String field;
    private final ValueOperation operation;
    private final Object value;

    public ValueFieldCondition(String joinTable, String field, ValueOperation operation, Object value) {
      this.joinTable = joinTable;
      this.field = field;
      this.operation = operation;
      this.value = value;
    }
  }

  public enum ValueOperation {
    EQUAL, EQUAL_IGNORECASE, LIKE, LIKE_IGNORECASE, IN, START_WITH, START_WITH_IGNORECASE, END_WITH, END_WITH_IGNORECASE
  }

}
