package com.jpa.library.dto;

import com.jpa.library.enums.BookStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookForm {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotNull(message = "저자를 입력해주세요.")
    private Long authorId;

    @NotNull(message = "출판사를 입력해주세요.")
    private Long publisherId;

    @NotBlank(message = "책 상태를 입력해주세요.")
    private String status;

    @Min(value = 0, message = "책 수량은 0권 이상이어야 합니다.")
    @NotNull(message = "책 수량을 입력해주세요.")
    private Integer totalQuantity;
}
