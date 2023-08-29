package com.timestat.timestatapi.repositories;

import com.timestat.timestatapi.models.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationRepository extends JpaRepository<Relation, Long> {
}
