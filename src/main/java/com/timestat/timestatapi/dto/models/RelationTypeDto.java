package com.timestat.timestatapi.dto.models;

import com.timestat.timestatapi.dto.Dto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RelationTypeDto extends Dto {
    private Long id;
    private String name;
    private String icon;
    private String color;

    public RelationTypeDto(Long id, String name, String icon, String color) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.color = color;
    }
}
