package com.timestat.timestatapi.dto.request;

import com.timestat.timestatapi.dto.RequestParamsDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventRequestParamsDto extends RequestParamsDto {
    List<Long> typeIdIn;
    List<Long> subjectIdIn;
    boolean approved;
    Date dateStart;
    Date dateEnd;

    public EventRequestParamsDto(int page, int pageSize, String search, List<Long> idIn, List<Long> typeIdIn, List<Long> subjectIdIn, boolean approved, Date dateStart, Date dateEnd) {
        this.setPage(page);
        this.setPageSize(pageSize);
        this.setSearch(search);
        this.setIdIn(idIn);
        this.typeIdIn = typeIdIn;
        this.subjectIdIn = subjectIdIn;
        this.approved = approved;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}
