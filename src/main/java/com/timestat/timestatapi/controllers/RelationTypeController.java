package com.timestat.timestatapi.controllers;

import com.timestat.timestatapi.dto.models.RelationTypeDto;
import com.timestat.timestatapi.models.RelationType;
import com.timestat.timestatapi.service.RelationTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/relation_types")
public class RelationTypeController {
    private final RelationTypeService service;

    public RelationTypeController(RelationTypeService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<RelationTypeDto>> getList() {
        return new ResponseEntity<>(service.list().stream().map(RelationType::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<RelationTypeDto> getById(@PathVariable Long id) {
        RelationType relationType = service.oneById(id);
        if (relationType == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(relationType.toDto(), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<RelationTypeDto> create(@RequestBody RelationTypeDto relationTypeDto) {
        try {
            RelationType createdRelationType = service.create(relationTypeDto);
            return new ResponseEntity<>(createdRelationType.toDto(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<RelationTypeDto> update(@PathVariable Long id, @RequestBody RelationTypeDto relationTypeDto) {
        try {
            RelationType updatedRelationType = service.update(id, relationTypeDto);
            return new ResponseEntity<>(updatedRelationType.toDto(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<RelationTypeDto> delete(@PathVariable Long id) {
        if (!service.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
