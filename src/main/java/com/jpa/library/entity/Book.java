package com.jpa.library.entity;

import com.jpa.library.dto.BookLoanForm;
import com.jpa.library.enums.BookStatus;
import com.jpa.library.util.LoanUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    private Integer totalQuantity;

    private Book(String title, Author author, Publisher publisher, BookStatus status, int totalQuantity) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.status = status;
        this.totalQuantity = totalQuantity;
    }

    public static Book createBook(String title, Author author, Publisher publisher, BookStatus status, int totalQuantity) {
        if (status == BookStatus.IN_STOCK && totalQuantity <= 0) {
            throw new IllegalArgumentException("보유중 상태일 땐 책의 수량은 0보다 커야 합니다.");
        }
        if (status == BookStatus.OUT_OF_STOCK && !(totalQuantity == 0)) {
            throw new IllegalArgumentException("미보유 상태일 땐 책의 수량은 0이어야합니다.");
        }
        return new Book(title, author, publisher, status, totalQuantity);
    }

    public BookLoan loan(BookLoanForm bookLoanForm, int unreturnedQuantity) {
        if (this.status == BookStatus.OUT_OF_STOCK) {
            throw new IllegalArgumentException("대여 불가능한 책입니다.");
        }
        if ((unreturnedQuantity + 1) >= totalQuantity) {
            this.status = BookStatus.OUT_OF_STOCK;
        }
        LocalDateTime dueDate = bookLoanForm.getLoanDate().plusDays(LoanUtil.getLoanDays(this.totalQuantity));

        return new BookLoan(this, bookLoanForm.getBorrowerName(), bookLoanForm.getLoanDate(), dueDate);
    }

    public void changeStatus(BookStatus newStatus) {
        this.status = newStatus;
    }

}
