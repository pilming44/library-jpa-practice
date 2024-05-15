package com.jpa.library.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookLoanForm {
    private Long bookId;
    private String borrowerName;
    private LocalDateTime loanDate;
}
