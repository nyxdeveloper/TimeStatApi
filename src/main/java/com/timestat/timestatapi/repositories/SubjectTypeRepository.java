package com.timestat.timestatapi.repositories;

import com.timestat.timestatapi.models.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectTypeRepository extends JpaRepository<SubjectType, Long>, JpaSpecificationExecutor<SubjectType> {
}
