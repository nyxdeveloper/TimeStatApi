package com.timestat.timestatapi.response;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private int currentPage;
    private Long totalItems;
    private int totalPages;
    private List<T> results;

    public PageResponse(int currentPage, Long totalItems, int totalPages, List<T> results) {
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.results = results;
    }
}
