package com.jpa.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultWrapper<T> {
    private T result;
}
