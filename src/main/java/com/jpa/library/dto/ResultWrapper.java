package com.jpa.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResultWrapper<T> {
    private T result;
    private Long totalCount;
    private int pageNo;
    private int pageSize;
    private int totalPage;

    public ResultWrapper(T result) {
        this.result = result;
    }

    public ResultWrapper(T result, Long totalCount, int pageNo, int pageSize, int totalPage) {
        this.result = result;
        this.totalCount = totalCount;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
    }
}
