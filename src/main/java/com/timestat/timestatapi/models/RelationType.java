package com.timestat.timestatapi.models;

import com.timestat.timestatapi.dto.RelationTypeDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "relation_types")
public class RelationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "icon", columnDefinition = "text")
    private String icon;

    @Column(name = "color")
    private String color;

    public RelationType() {

    }

    public RelationType(Long id, String name, String icon, String color) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.color = color;
    }

    public RelationTypeDto toDto() {
        return new RelationTypeDto(id, name, icon, color);
    }
}
