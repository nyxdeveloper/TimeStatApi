package com.timestat.timestatapi.controllers;

import com.timestat.timestatapi.dto.models.EventDto;
import com.timestat.timestatapi.dto.request.EventRequestParamsDto;
import com.timestat.timestatapi.dto.response.PageResponseDto;
import com.timestat.timestatapi.models.Event;
import com.timestat.timestatapi.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<PageResponseDto<Event>> getList(@RequestParam EventRequestParamsDto request) {
        Page<Event> resultPage = service.paginateList(request);
        PageResponseDto<Event> pageResponse = new PageResponseDto<>(resultPage);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<EventDto> getById(@PathVariable Long id) {
        Event event = service.oneById(id);
        if (event == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(event.toDto(), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<EventDto> create(@RequestBody EventDto eventDto) {
        try {
            Event createdEvent = service.create(eventDto);
            return new ResponseEntity<>(createdEvent.toDto(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<EventDto> update(@PathVariable Long id, @RequestBody EventDto eventDto) {
        try {
            Event updatedEvent = service.update(id, eventDto);
            return new ResponseEntity<>(updatedEvent.toDto(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EventDto> delete(@PathVariable Long id) {
        if (!service.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
