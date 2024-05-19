package com.jpa.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class ErrorResult {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
