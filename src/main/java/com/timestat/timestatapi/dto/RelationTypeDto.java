package com.timestat.timestatapi.dto;

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
