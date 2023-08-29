package com.timestat.timestatapi.response;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private int currentPage;
    private Long totalItems;
    private int totalPages;
    private List<T> results;
}
