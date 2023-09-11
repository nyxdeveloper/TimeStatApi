package com.timestat.timestatapi.service;

import com.timestat.timestatapi.dto.models.EventDto;
import com.timestat.timestatapi.dto.request.EventRequestParamsDto;
import com.timestat.timestatapi.models.Event;
import com.timestat.timestatapi.models.EventType;
import com.timestat.timestatapi.models.Subject;
import com.timestat.timestatapi.repositories.EventRepository;
import com.timestat.timestatapi.repositories.EventTypeRepository;
import com.timestat.timestatapi.repositories.SubjectRepository;
import com.timestat.timestatapi.specifications.EventSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EventService {
    private final EventRepository repository;
    private final EventTypeRepository eventTypeRepository;
    private final SubjectRepository subjectRepository;

    public EventService(EventRepository repository, EventTypeRepository eventTypeRepository, SubjectRepository subjectRepository) {
        this.repository = repository;
        this.eventTypeRepository = eventTypeRepository;
        this.subjectRepository = subjectRepository;
    }

    public Page<Event> paginateList(EventRequestParamsDto request) {
        Specification<Event> specification = EventSpecification.getFilterSpecification(request);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getPageSize(), Sort.by("date").descending());
        return repository.findAll(specification, pageRequest);
    }

    public Event oneById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Transactional
    public Event save(Event event) {
        return repository.save(event);
    }

    @Transactional
    public Event create(EventDto dto) {
        Long eventTypeId = dto.getTypeId();
        Long subjectId = dto.getSubjectId();
        EventType eventType = eventTypeRepository.findById(eventTypeId).orElse(null);
        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        if (eventType == null)
            throw new RuntimeException("EventType with id " + eventTypeId + " not found");
        if (subject == null)
            throw new RuntimeException("Subject with id " + subjectId + " not found");
        return save(new Event(0L, dto.getName(), dto.getDescription(), dto.getDate(), dto.getApproved(), eventType, dto.getSourceHref(), subject));
    }

    @Transactional
    public Event update(Long id, EventDto dto) {
        Event event = oneById(id);
        if (event == null)
            throw new RuntimeException("Event with id " + id + " not found");
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        Long eventTypeId = dto.getTypeId();
        if (!Objects.equals(event.getType().getId(), eventTypeId)) {
            EventType eventType = eventTypeRepository.findById(eventTypeId).orElse(null);
            if (eventType == null)
                throw new RuntimeException("EventType with id " + eventTypeId + " not found");
            event.setType(eventType);
        }
        Long subjectId = dto.getSubjectId();
        if (!Objects.equals(event.getSubject().getId(), subjectId)) {
            Subject subject = subjectRepository.findById(subjectId).orElse(null);
            if (subject == null)
                throw new RuntimeException("Subject with id " + subjectId + " not found");
            event.setSubject(subject);
        }
        return save(event);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
