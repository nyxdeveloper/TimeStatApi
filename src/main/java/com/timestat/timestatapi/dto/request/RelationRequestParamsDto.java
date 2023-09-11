package com.timestat.timestatapi.dto.request;

import com.timestat.timestatapi.dto.RequestParamsDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RelationRequestParamsDto extends RequestParamsDto {
    List<Long> typeIdIn;
    List<Long> fromIdIn;
    List<Long> toIdIn;

    public RelationRequestParamsDto(int page, int pageSize, String search, List<Long> idIn, List<Long> typeIdIn, List<Long> fromIdIn, List<Long> toIdIn) {
        this.setPage(page);
        this.setPageSize(pageSize);
        this.setSearch(search);
        this.setIdIn(idIn);
        this.typeIdIn = typeIdIn;
        this.fromIdIn = fromIdIn;
        this.toIdIn = toIdIn;
    }
}
