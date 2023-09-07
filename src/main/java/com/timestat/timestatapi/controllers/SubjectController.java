package com.timestat.timestatapi.controllers;

import com.timestat.timestatapi.dto.PageResponseDto;
import com.timestat.timestatapi.dto.SubjectDto;
import com.timestat.timestatapi.models.Subject;
import com.timestat.timestatapi.service.SubjectService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    private final SubjectService service;

    public SubjectController(SubjectService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<PageResponseDto<Subject>> getList(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "100") int pageSize,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "") List<Long> idIn,
            @RequestParam(required = false, defaultValue = "") List<Long> typeIdIn
    ) {
        Page<Subject> resultPage = service.paginateList(page, pageSize, search, idIn, typeIdIn);
        PageResponseDto<Subject> pageResponse = new PageResponseDto<>(resultPage);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<SubjectDto> getById(@PathVariable Long id) {
        Subject subject = service.oneById(id);
        if (subject == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(subject.toDto(), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<SubjectDto> create(@RequestBody SubjectDto subjectDto) {
        try {
            Subject createdSubject = service.create(subjectDto);
            return new ResponseEntity<>(createdSubject.toDto(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<SubjectDto> update(@PathVariable Long id, @RequestBody SubjectDto subjectDto) {
        try {
            Subject updatedSubject = service.update(id, subjectDto);
            return new ResponseEntity<>(updatedSubject.toDto(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<SubjectDto> delete(@PathVariable Long id) {
        if (!service.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
