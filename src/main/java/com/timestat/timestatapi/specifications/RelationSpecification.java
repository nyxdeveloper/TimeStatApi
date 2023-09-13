package com.timestat.timestatapi.specifications;

import com.timestat.timestatapi.dto.request.RelationRequestParamsDto;
import com.timestat.timestatapi.models.Relation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RelationSpecification {
    public static Specification<Relation> getFilterSpecification(RelationRequestParamsDto request) {
        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> filterPredicates = new ArrayList<>();

        return (root, query, cb) -> {
            String search = request.getSearch();
            if (search != null) {
                List<Predicate> searchPredicates = new ArrayList<>();
                searchPredicates.add(cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%"));
                searchPredicates.add(cb.like(cb.lower(root.get("description")), "%" + search.toLowerCase() + "%"));
                predicates.add(cb.or(searchPredicates.toArray(new Predicate[0])));
            }

            List<Long> idIn = request.getIdIn();
            if (idIn != null) {
                CriteriaBuilder.In<Long> idInPredicate = cb.in(root.get("id"));
                for (Long id : idIn) {
                    idInPredicate.value(id);
                }
                filterPredicates.add(idInPredicate);
            }

            List<Long> typeIdIn = request.getTypeIdIn();
            if (typeIdIn != null) {
                CriteriaBuilder.In<Long> typeIdInPredicate = cb.in(root.get("type").get("id"));
                for (Long typeId : typeIdIn) {
                    typeIdInPredicate.value(typeId);
                }
                filterPredicates.add(typeIdInPredicate);
            }

            List<Long> fromIdIn = request.getFromIdIn();
            if (fromIdIn != null) {
                CriteriaBuilder.In<Long> fromIdInPredicate = cb.in(root.get("from").get("id"));
                for (Long fromId : fromIdIn) {
                    fromIdInPredicate.value(fromId);
                }
                filterPredicates.add(fromIdInPredicate);
            }
            List<Long> toIdIn = request.getFromIdIn();
            if (toIdIn != null) {
                CriteriaBuilder.In<Long> toIdInPredicate = cb.in(root.get("to").get("id"));
                for (Long toId : toIdIn) {
                    toIdInPredicate.value(toId);
                }
                filterPredicates.add(toIdInPredicate);
            }
            if (!filterPredicates.isEmpty()) {
                predicates.add(cb.and(filterPredicates.toArray(new Predicate[0])));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
