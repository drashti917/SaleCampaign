package com.example.demo.schedular.model;

import java.util.List;

public class PaginationDto {
    private List<Product> product;
    private int pageNo;
    private int pageSize;
    private int totalPages;

    public PaginationDto(List<Product> product, int pageNo, int pageSize, int totalPages) {
        this.product = product;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
    }

    public List<Product> getProduct() {
        return product;
    }


    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
