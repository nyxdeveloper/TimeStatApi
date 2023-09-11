package com.timestat.timestatapi.service;

import com.timestat.timestatapi.dto.models.SubjectDto;
import com.timestat.timestatapi.dto.request.SubjectRequestParamsDto;
import com.timestat.timestatapi.models.Subject;
import com.timestat.timestatapi.models.SubjectType;
import com.timestat.timestatapi.repositories.SubjectRepository;
import com.timestat.timestatapi.repositories.SubjectTypeRepository;
import com.timestat.timestatapi.specifications.SubjectSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SubjectService {
    private final SubjectRepository repository;
    private final SubjectTypeRepository subjectTypeRepository;

    public SubjectService(SubjectRepository repository, SubjectTypeRepository subjectTypeRepository) {
        this.repository = repository;
        this.subjectTypeRepository = subjectTypeRepository;
    }

    public Page<Subject> paginateList(SubjectRequestParamsDto request) {
        Specification<Subject> specification = SubjectSpecification.getFilterSpecification(request);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getPageSize(), Sort.by("name"));
        return repository.findAll(specification, pageRequest);
    }

    public Subject oneById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Transactional
    public Subject save(Subject subject) {
        return repository.save(subject);
    }

    @Transactional
    public Subject create(SubjectDto dto) {
        Long subjectTypeId = dto.getTypeId();
        SubjectType subjectType = subjectTypeRepository.findById(subjectTypeId).orElse(null);
        if (subjectType == null)
            throw new RuntimeException("SubjectType with id " + subjectTypeId + " not found");
        return save(new Subject(0L, dto.getName(), dto.getDescription(), subjectType));
    }

    @Transactional
    public Subject update(Long id, SubjectDto dto) {
        Subject subject = oneById(id);
        if (subject == null)
            throw new RuntimeException("Subject with id " + id + " not found");
        subject.setName(dto.getName());
        subject.setDescription(dto.getDescription());
        Long subjectTypeId = dto.getTypeId();
        if (!Objects.equals(subject.getType().getId(), subjectTypeId)) {
            SubjectType subjectType = subjectTypeRepository.findById(subjectTypeId).orElse(null);
            if (subjectType == null)
                throw new RuntimeException("SubjectType with id " + subjectTypeId + " not found");
            subject.setType(subjectType);
        }
        return save(subject);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
