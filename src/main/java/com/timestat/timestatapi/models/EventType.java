package com.timestat.timestatapi.models;

import com.timestat.timestatapi.dto.models.EventTypeDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "event_types")
public class EventType implements IBaseEntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "icon", columnDefinition = "text")
    private String icon;

    public EventType() {

    }

    public EventType(Long id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public EventTypeDto toDto() {
        return new EventTypeDto(id, name, icon);
    }
}
