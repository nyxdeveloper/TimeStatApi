package com.timestat.timestatapi.controllers;

import com.timestat.timestatapi.dto.models.RelationDto;
import com.timestat.timestatapi.dto.request.RelationRequestParamsDto;
import com.timestat.timestatapi.dto.response.PageResponseDto;
import com.timestat.timestatapi.models.Relation;
import com.timestat.timestatapi.service.RelationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relations")
public class RelationController {
    private final RelationService service;

    public RelationController(RelationService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<PageResponseDto<Relation>> getList(@RequestParam RelationRequestParamsDto request) {
        Page<Relation> resultPage = service.paginateList(request);
        PageResponseDto<Relation> pageResponse = new PageResponseDto<>(resultPage);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<RelationDto> getById(@PathVariable Long id) {
        Relation relation = service.oneById(id);
        if (relation == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(relation.toDto(), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<RelationDto> create(@RequestBody RelationDto relationDto) {
        try {
            Relation createdRelation = service.create(relationDto);
            return new ResponseEntity<>(createdRelation.toDto(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<RelationDto> update(@PathVariable Long id, @RequestBody RelationDto relationDto) {
        try {
            Relation updatedRelation = service.update(id, relationDto);
            return new ResponseEntity<>(updatedRelation.toDto(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<RelationDto> delete(@PathVariable Long id) {
        if (!service.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
