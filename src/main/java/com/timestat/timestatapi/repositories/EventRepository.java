package com.timestat.timestatapi.repositories;

import com.timestat.timestatapi.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

}
