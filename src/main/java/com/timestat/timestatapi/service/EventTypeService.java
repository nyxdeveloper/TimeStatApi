package com.timestat.timestatapi.service;

import com.timestat.timestatapi.dto.models.EventTypeDto;
import com.timestat.timestatapi.models.EventType;
import com.timestat.timestatapi.repositories.EventTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTypeService {
    private final EventTypeRepository repository;

    public EventTypeService(EventTypeRepository repository) {
        this.repository = repository;
    }

    public List<EventType> list() {
        return repository.findAll();
    }

    public EventType oneById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Transactional
    public EventType save(EventType eventType) {
        return repository.save(eventType);
    }

    @Transactional
    public EventType create(EventTypeDto dto) {
        return save(new EventType(0L, dto.getName(), dto.getIcon()));
    }

    @Transactional
    public EventType update(Long id, EventTypeDto dto) {
        EventType eventType = oneById(id);
        if (eventType == null)
            throw new RuntimeException("EventType with id " + id + " not found");
        eventType.setName(dto.getName());
        eventType.setIcon(dto.getIcon());
        return save(eventType);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
