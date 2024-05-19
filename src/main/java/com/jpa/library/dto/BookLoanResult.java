package com.jpa.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookLoanResult {
    private String title;
    private String borrowerName;
    private LocalDateTime loanDate;
    private LocalDateTime dueDate;
}
