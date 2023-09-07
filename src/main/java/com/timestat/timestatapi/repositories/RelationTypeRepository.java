package com.timestat.timestatapi.repositories;

import com.timestat.timestatapi.models.RelationType;
import com.timestat.timestatapi.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationTypeRepository extends JpaRepository<RelationType, Long>, JpaSpecificationExecutor<Subject> {
}
