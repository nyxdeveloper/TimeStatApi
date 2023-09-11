package com.timestat.timestatapi.dto.models;

import com.timestat.timestatapi.dto.Dto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RelationDto extends Dto {
    private Long id;
    private Long typeId;
    private RelationTypeDto type;
    private String description;
    private Long fromId;
    private Long toId;

    public RelationDto(Long id, String description, RelationTypeDto type, Long fromId, Long toId) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.typeId = type.getId();
        this.fromId = fromId;
        this.toId = toId;
    }
}
