package com.timestat.timestatapi.service;

import com.timestat.timestatapi.dto.RelationTypeDto;
import com.timestat.timestatapi.models.RelationType;
import com.timestat.timestatapi.repositories.RelationTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationTypeService {
    private final RelationTypeRepository repository;

    public RelationTypeService(RelationTypeRepository repository) {
        this.repository = repository;
    }

    public List<RelationType> list() {
        return repository.findAll();
    }

    public RelationType oneById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Transactional
    public RelationType save(RelationType relationType) {
        return repository.save(relationType);
    }

    @Transactional
    public RelationType create(RelationTypeDto dto) {
        return save(new RelationType(0L, dto.getName(), dto.getIcon(), dto.getColor()));
    }

    @Transactional
    public RelationType update(Long id, RelationTypeDto dto) {
        RelationType relationType = oneById(id);
        if (relationType == null)
            throw new RuntimeException("RelationType with id " + id + " not found");
        relationType.setName(dto.getName());
        relationType.setIcon(dto.getIcon());
        relationType.setColor(dto.getColor());
        return save(relationType);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
