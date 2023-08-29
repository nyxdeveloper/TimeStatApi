package com.timestat.timestatapi.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventDto extends Dto {
    private Long id;
    private String name;
    private String description;
    private Date date;
    private Boolean approved;
    private EventTypeDto type;
    private Long typeId;
    private String sourceHref;
    private Long subjectId;

    public EventDto(Long id, String name, String description, Date date, Boolean approved, EventTypeDto type, String sourceHref, Long subjectId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.approved = approved;
        this.type = type;
        this.typeId = type.getId();
        this.sourceHref = sourceHref;
        this.subjectId = subjectId;
    }
}
