package com.timestat.timestatapi.dto.models;

import com.timestat.timestatapi.dto.Dto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SubjectTypeDto extends Dto {
    private Long id;
    private String name;
    private String icon;

    public SubjectTypeDto(Long id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }
}
