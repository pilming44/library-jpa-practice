package com.jpa.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookLoanForm {
    @NotNull(message = "대여하려는 책 ID를 입력해주세요.")
    private Long bookId;

    @NotBlank(message = "대여자 이름을 입력해주세요.")
    private String borrowerName;

    @NotNull(message = "대여일시를 입력해주세요.")
    private LocalDateTime loanDate;
}
