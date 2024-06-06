package com.jpa.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookReturnForm {
    @NotBlank(message = "책 이름을 입력해주세요.")
    private String bookName;

    @NotBlank(message = "대여자 이름을 입력해주세요.")
    private String borrowerName;

    @NotNull(message = "반납일시를 입력해주세요.")
    private LocalDateTime returnDate;
}
