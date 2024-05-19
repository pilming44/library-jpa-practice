package com.jpa.library.entity;

import com.jpa.library.dto.BookLoanForm;
import com.jpa.library.enums.BookStatus;
import com.jpa.library.util.LoanPeriodUtil;
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

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    private int totalQuantity;

    public Book(String title, Author author, Publisher publisher, BookStatus status, int totalQuantity) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.status = status;
        this.totalQuantity = totalQuantity;
    }

    public int getLoanQuantity(int loanQuantity) {
        return totalQuantity - loanQuantity;
    }

    public BookLoan loan(BookLoanForm bookLoanForm, int unreturnedQuantity) {
        if (this.status == BookStatus.UNAVAILABLE) {
            throw new IllegalArgumentException("대여 불가능한 책입니다.");
        }
        if ((unreturnedQuantity + 1) >= totalQuantity) {
            this.status  = BookStatus.UNAVAILABLE;
        }
        LocalDateTime dueDate = bookLoanForm.getLoanDate().plusDays(LoanPeriodUtil.getLoanDays(this.totalQuantity));

        return new BookLoan(this, bookLoanForm.getBorrowerName(), bookLoanForm.getLoanDate(), dueDate);
    }

}
