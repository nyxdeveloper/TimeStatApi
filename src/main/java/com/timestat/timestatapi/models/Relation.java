package com.timestat.timestatapi.models;

import com.timestat.timestatapi.dto.RelationDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "relations")
public class Relation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private RelationType type;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "from_id")
    private Event from;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "to_id")
    private Event to;

    public Relation() {

    }

    public Relation(Long id, String description, RelationType type, Event from, Event to) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.from = from;
        this.to = to;
    }

    public RelationDto toDto() {
        return new RelationDto(id, description, type.toDto(), from.getId(), to.getId());
    }
}
