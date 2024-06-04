package com.jpa.library.dto;

import com.jpa.library.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDetail {
    private Long id;
    private String title;
    private AuthorInfo authorInfo;
    private PublisherInfo publisherInfo;
    private BookStatus bookStatus;
    private Integer totalQuantity;
    private List<BookLoanStatus> bookLoanStatusList;
}
