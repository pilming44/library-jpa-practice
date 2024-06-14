package com.jpa.library.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookSummary {
    private Long id;
    private String title;
    private String authorName;
    private String publisherName;
    private Long totalQuantity;
    private Long loanQuantity;
}
