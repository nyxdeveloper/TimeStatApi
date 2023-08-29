package com.timestat.timestatapi.models;

import com.timestat.timestatapi.dto.EventDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "date")
    private Date date;

    @Column(name = "approved")
    private Boolean approved;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private EventType type;

    @Column(name = "source_href")
    private String sourceHref;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public Event() {

    }

    public Event(Long id, String name, String description, Date date, Boolean approved, EventType type, String sourceHref, Subject subject) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.approved = approved;
        this.type = type;
        this.sourceHref = sourceHref;
        this.subject = subject;
    }

    public EventDto toDto() {
        return new EventDto(id, name, description, date, approved, type.toDto(), sourceHref, subject.getId());
    }
}
