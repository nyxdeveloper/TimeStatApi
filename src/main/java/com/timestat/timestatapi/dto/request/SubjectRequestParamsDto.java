package com.timestat.timestatapi.dto.request;

import com.timestat.timestatapi.dto.RequestParamsDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SubjectRequestParamsDto extends RequestParamsDto {
    List<Long> typeIdIn;
}
