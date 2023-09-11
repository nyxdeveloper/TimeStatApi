package com.timestat.timestatapi.dto.models;

import com.timestat.timestatapi.dto.Dto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SubjectDto extends Dto {
    private Long id;
    private String name;
    private String description;
    private Long typeId;
    private SubjectTypeDto type;

    public SubjectDto(Long id, String name, String description, SubjectTypeDto type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.typeId = type.getId();
    }
}
