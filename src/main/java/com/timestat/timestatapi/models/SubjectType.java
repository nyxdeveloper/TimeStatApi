package com.timestat.timestatapi.models;

import com.timestat.timestatapi.dto.SubjectTypeDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "subject_types")
public class SubjectType implements IBaseEntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "icon", columnDefinition = "text")
    private String icon;

    public SubjectType() {

    }

    public SubjectType(Long id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public SubjectTypeDto toDto() {
        return new SubjectTypeDto(id, name, icon);
    }
}
