package com.timestat.timestatapi.specifications;

import com.timestat.timestatapi.dto.request.EventRequestParamsDto;
import com.timestat.timestatapi.models.Event;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventSpecification {
    public static Specification<Event> getFilterSpecification(EventRequestParamsDto request) {
        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> filterPredicates = new ArrayList<>();

        return (root, query, cb) -> {
            predicates.add(cb.equal(root.get("approved"), request.isApproved()));

            String search = request.getSearch();
            if (search != null) {
                List<Predicate> searchPredicates = new ArrayList<>();
                searchPredicates.add(cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%"));
                searchPredicates.add(cb.like(cb.lower(root.get("description")), "%" + search.toLowerCase() + "%"));
                predicates.add(cb.or(searchPredicates.toArray(new Predicate[0])));
            }

            Date dateStart = request.getDateStart();
            if (dateStart != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("date"), dateStart));
            }

            Date dateEnd = request.getDateEnd();
            if (dateEnd != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("date"), dateEnd));
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

            List<Long> subjectIdIn = request.getSubjectIdIn();
            if (subjectIdIn != null) {
                CriteriaBuilder.In<Long> subjectIdInPredicate = cb.in(root.get("subject").get("id"));
                for (Long subjectId : subjectIdIn) {
                    subjectIdInPredicate.value(subjectId);
                }
                filterPredicates.add(subjectIdInPredicate);
            }
            if (!filterPredicates.isEmpty()) {
                predicates.add(cb.and(filterPredicates.toArray(new Predicate[0])));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
