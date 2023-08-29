package com.timestat.timestatapi.models;

import com.timestat.timestatapi.dto.SubjectDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private SubjectType type;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subject")
    private List<Event> events;

    public Subject() {

    }

    public Subject(Long id, String name, String description, SubjectType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public SubjectDto toDto() {
        return new SubjectDto(id, name, description, type.toDto());
    }
}
