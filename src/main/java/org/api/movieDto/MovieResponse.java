package org.api.movieDto;

import java.util.List;

public class MovieResponse {
    
    private List<MovieDto> results;
    private int page;
    private int total_results;
    private int total_pages;


    public List<MovieDto> getResults() {
        return results;
    }

    public void setResults(List<MovieDto> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    @Override
    public String toString() {
        return "MovieResponse [results=" + results + ", page=" + page + ", total_results=" + total_results
                + ", total_pages=" + total_pages + ", getResults()=" + getResults() + ", getPage()=" + getPage()
                + ", getTotal_results()=" + getTotal_results() + ", getTotal_pages()=" + getTotal_pages()
                + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
                + "]";
    }

    

   
}
