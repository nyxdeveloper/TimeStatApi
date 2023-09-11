package com.timestat.timestatapi.service;

import com.timestat.timestatapi.dto.models.RelationDto;
import com.timestat.timestatapi.dto.request.RelationRequestParamsDto;
import com.timestat.timestatapi.models.Relation;
import com.timestat.timestatapi.models.RelationType;
import com.timestat.timestatapi.models.Event;
import com.timestat.timestatapi.repositories.RelationRepository;
import com.timestat.timestatapi.repositories.RelationTypeRepository;
import com.timestat.timestatapi.repositories.EventRepository;
import com.timestat.timestatapi.specifications.RelationSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RelationService {
    private final RelationRepository repository;
    private final RelationTypeRepository relationTypeRepository;
    private final EventRepository eventRepository;

    public RelationService(RelationRepository repository, RelationTypeRepository relationTypeRepository, EventRepository eventRepository) {
        this.repository = repository;
        this.relationTypeRepository = relationTypeRepository;
        this.eventRepository = eventRepository;
    }

    public Page<Relation> paginateList(RelationRequestParamsDto request) {
        Specification<Relation> specification = RelationSpecification.getFilterSpecification(request);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getPageSize(), Sort.by("id").descending());
        return repository.findAll(specification, pageRequest);
    }

    public Relation oneById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Transactional
    public Relation save(Relation relation) {
        return repository.save(relation);
    }

    @Transactional
    public Relation create(RelationDto dto) {
        Long relationTypeId = dto.getTypeId();
        Long fromEventId = dto.getFromId();
        Long toEventId = dto.getToId();
        RelationType relationType = relationTypeRepository.findById(relationTypeId).orElse(null);
        Event fromEvent = eventRepository.findById(fromEventId).orElse(null);
        Event toEvent = eventRepository.findById(toEventId).orElse(null);
        if (relationType == null)
            throw new RuntimeException("RelationType with id " + relationTypeId + " not found");
        if (fromEvent == null)
            throw new RuntimeException("Event with id " + fromEventId + " not found");
        if (toEvent == null)
            throw new RuntimeException("Event with id " + toEventId + " not found");
        return save(new Relation(0L, dto.getDescription(), relationType, fromEvent, toEvent));
    }

    @Transactional
    public Relation update(Long id, RelationDto dto) {
        Relation relation = oneById(id);
        if (relation == null)
            throw new RuntimeException("Relation with id " + id + " not found");
        relation.setDescription(dto.getDescription());
        Long relationTypeId = dto.getTypeId();
        if (!Objects.equals(relation.getType().getId(), relationTypeId)) {
            RelationType relationType = relationTypeRepository.findById(relationTypeId).orElse(null);
            if (relationType == null)
                throw new RuntimeException("RelationType with id " + relationTypeId + " not found");
            relation.setType(relationType);
        }
        Long fromEventId = dto.getFromId();
        if (!Objects.equals(relation.getFrom().getId(), fromEventId)) {
            Event fromEvent = eventRepository.findById(fromEventId).orElse(null);
            if (fromEvent == null)
                throw new RuntimeException("Event with id " + fromEventId + " not found");
            relation.setFrom(fromEvent);
        }
        Long toEventId = dto.getFromId();
        if (!Objects.equals(relation.getTo().getId(), toEventId)) {
            Event toEvent = eventRepository.findById(toEventId).orElse(null);
            if (toEvent == null)
                throw new RuntimeException("Event with id " + toEventId + " not found");
            relation.setFrom(toEvent);
        }
        return save(relation);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
