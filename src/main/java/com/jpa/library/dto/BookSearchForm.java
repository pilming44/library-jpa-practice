package com.jpa.library.dto;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookSearchForm {
    private String title;
    private String authorName;
    private String publisherName;

    @Min(value = 1, message = "페이지는 1보다 큰 양수여야됩니다.")
    private int page = 1;

    @Min(value = 1, message = "사이즈는 1보다 큰 양수여야됩니다.")
    private int size = 10;
}
