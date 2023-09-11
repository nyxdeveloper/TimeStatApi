package com.timestat.timestatapi.service;

import com.timestat.timestatapi.dto.models.SubjectTypeDto;
import com.timestat.timestatapi.models.SubjectType;
import com.timestat.timestatapi.repositories.SubjectTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectTypeService {
    private final SubjectTypeRepository repository;

    public SubjectTypeService(SubjectTypeRepository repository) {
        this.repository = repository;
    }

    public List<SubjectType> list() {
        return repository.findAll();
    }

    public SubjectType oneById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Transactional
    public SubjectType save(SubjectType subjectType) {
        return repository.save(subjectType);
    }

    @Transactional
    public SubjectType create(SubjectTypeDto dto) {
        return save(new SubjectType(0L, dto.getName(), dto.getIcon()));
    }

    @Transactional
    public SubjectType update(Long id, SubjectTypeDto dto) {
        SubjectType subjectType = oneById(id);
        if (subjectType == null)
            throw new RuntimeException("SubjectType with id " + id + " not found");
        subjectType.setName(dto.getName());
        subjectType.setIcon(dto.getIcon());
        return save(subjectType);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
