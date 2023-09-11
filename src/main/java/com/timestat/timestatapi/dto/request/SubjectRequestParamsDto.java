package com.timestat.timestatapi.dto.request;

import com.timestat.timestatapi.dto.RequestParamsDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SubjectRequestParamsDto extends RequestParamsDto {
    List<Long> typeIdIn;

    public SubjectRequestParamsDto(int page, int pageSize, String search, List<Long> idIn, List<Long> typeIdIn) {
        this.setPage(page);
        this.setPageSize(pageSize);
        this.setSearch(search);
        this.setIdIn(idIn);
        this.typeIdIn = typeIdIn;
    }
}
