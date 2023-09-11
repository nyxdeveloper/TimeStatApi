package com.timestat.timestatapi.controllers;

import com.timestat.timestatapi.dto.models.SubjectTypeDto;
import com.timestat.timestatapi.models.SubjectType;
import com.timestat.timestatapi.service.SubjectTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subject_types")
public class SubjectTypeController {
    private final SubjectTypeService service;

    public SubjectTypeController(SubjectTypeService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<SubjectTypeDto>> getList() {
        return new ResponseEntity<>(service.list().stream().map(SubjectType::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<SubjectTypeDto> getById(@PathVariable Long id) {
        SubjectType subjectType = service.oneById(id);
        if (subjectType == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(subjectType.toDto(), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<SubjectTypeDto> create(@RequestBody SubjectTypeDto subjectTypeDto) {
        try {
            SubjectType createdSubjectType = service.create(subjectTypeDto);
            return new ResponseEntity<>(createdSubjectType.toDto(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<SubjectTypeDto> update(@PathVariable Long id, @RequestBody SubjectTypeDto subjectTypeDto) {
        try {
            SubjectType updatedSubjectType = service.update(id, subjectTypeDto);
            return new ResponseEntity<>(updatedSubjectType.toDto(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<SubjectTypeDto> delete(@PathVariable Long id) {
        if (!service.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
