package com.timestat.timestatapi.specifications;

import com.timestat.timestatapi.dto.request.SubjectRequestParamsDto;
import com.timestat.timestatapi.models.Subject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

public class SubjectSpecification {
    public static Specification<Subject> getFilterSpecification(SubjectRequestParamsDto request) {
        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> filterPredicates = new ArrayList<>();

        return (root, query, cb) -> {
            String search = request.getSearch();
            if (!search.isEmpty()) {
                List<Predicate> searchPredicates = new ArrayList<>();
                searchPredicates.add(cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%"));
                searchPredicates.add(cb.like(cb.lower(root.get("description")), "%" + search.toLowerCase() + "%"));
                predicates.add(cb.or(searchPredicates.toArray(new Predicate[0])));
            }
            List<Long> idIn = request.getIdIn();
            if (!idIn.isEmpty()) {
                CriteriaBuilder.In<Long> idInPredicate = cb.in(root.get("id"));
                for (Long id : idIn) {
                    idInPredicate.value(id);
                }
                filterPredicates.add(idInPredicate);
            }
            List<Long> typeIdIn = request.getTypeIdIn();
            if (!typeIdIn.isEmpty()) {
                CriteriaBuilder.In<Long> typeIdInPredicate = cb.in(root.get("type").get("id"));
                for (Long typeId : typeIdIn) {
                    typeIdInPredicate.value(typeId);
                }
                filterPredicates.add(typeIdInPredicate);
            }
            if (!filterPredicates.isEmpty()) {
                predicates.add(cb.and(filterPredicates.toArray(new Predicate[0])));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
