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
public class BookInfo {
    private Long id;
    private String title;
    private AuthorInfo authorInfo;
    private PublisherInfo publisherInfo;
    private BookStatus bookStatus;
    private Long totalQuantity;
}
