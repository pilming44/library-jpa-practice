package com.jpa.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@NoArgsConstructor
public class BookLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_loan_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private String borrowerName;

    private LocalDateTime loanDate;

    private LocalDateTime dueDate;

    private LocalDateTime returnDate;

    public BookLoan(Book book, String borrowerName, LocalDateTime loanDate, LocalDateTime dueDate) {
        this.book = book;
        this.borrowerName = borrowerName;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public Integer getOverdueDays(LocalDateTime baseTime) {
        Long overdueDays = ChronoUnit.DAYS.between(dueDate, baseTime);
        if (overdueDays < 0L) {
            return 0;
        }

        if (overdueDays > Integer.MAX_VALUE || overdueDays < Integer.MIN_VALUE) {
            throw new IllegalArgumentException("연체가 너무 오래됐습니다.");
        }
        return overdueDays.intValue();
    }
}
