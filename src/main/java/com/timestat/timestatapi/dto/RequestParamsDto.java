package com.timestat.timestatapi.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RequestParamsDto extends Dto {
    String search;
    List<Long> idIn;
    int page;
    int pageSize;
}
