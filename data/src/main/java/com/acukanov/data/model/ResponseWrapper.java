package com.acukanov.data.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseWrapper {
    private static final String PAGE = "page";
    private static final String TOTAL_RESULTS = "total_results";
    private static final String TOTAL_PAGES = "total_pages";
    private static final String RESULTS = "results";

    @SerializedName(PAGE)
    private int page;
    @SerializedName(TOTAL_RESULTS)
    private int totalResults;
    @SerializedName(TOTAL_PAGES)
    private int totalPages;
    @SerializedName(RESULTS)
    private List<Results> results;

    // region getters and setters

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    // endregion
}
