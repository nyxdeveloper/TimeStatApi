package com.timestat.timestatapi.dto.response;

import com.timestat.timestatapi.dto.Dto;
import com.timestat.timestatapi.models.IBaseEntityModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageResponseDto<T extends IBaseEntityModel> extends Dto {
    private int currentPage;
    private Long totalItems;
    private int numberOfElements;
    private int totalPages;
    private int size;
    private String sort;
    private List<Dto> results;

    public PageResponseDto(Page<T> page) {
        this.currentPage = page.getNumber();
        this.totalItems = page.getTotalElements();
        this.numberOfElements = page.getNumberOfElements();
        this.totalPages = page.getTotalPages();
        this.size = page.getSize();
        this.sort = page.getSort().toString();
        this.results = page.getContent().stream().map(T::toDto).collect(Collectors.toList());
    }
}
