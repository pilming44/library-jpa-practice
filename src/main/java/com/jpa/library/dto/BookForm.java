package com.jpa.library.dto;

import com.jpa.library.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookForm {
    private String title;
    private Long authorId;
    private Long publisherId;
    private BookStatus status;
    private int totalQuantity;
}
