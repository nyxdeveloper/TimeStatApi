package com.timestat.timestatapi.controllers;

import com.timestat.timestatapi.dto.models.EventTypeDto;
import com.timestat.timestatapi.models.EventType;
import com.timestat.timestatapi.service.EventTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/event_types")
public class EventTypeController {
    private final EventTypeService service;

    public EventTypeController(EventTypeService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<EventTypeDto>> getList() {
        return new ResponseEntity<>(service.list().stream().map(EventType::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<EventTypeDto> getById(@PathVariable Long id) {
        EventType eventType = service.oneById(id);
        if (eventType == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(eventType.toDto(), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<EventTypeDto> create(@RequestBody EventTypeDto eventTypeDto) {
        try {
            EventType createdEventType = service.create(eventTypeDto);
            return new ResponseEntity<>(createdEventType.toDto(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EventTypeDto> update(@PathVariable Long id, @RequestBody EventTypeDto eventTypeDto) {
        try {
            EventType updatedEventType = service.update(id, eventTypeDto);
            return new ResponseEntity<>(updatedEventType.toDto(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EventTypeDto> delete(@PathVariable Long id) {
        if (!service.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
