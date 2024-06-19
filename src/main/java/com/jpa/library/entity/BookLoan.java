package com.jpa.library.entity;

import com.jpa.library.enums.BookStatus;
import com.jpa.library.util.OverdueFeeCalculator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_loan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
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
        Long overdueDays = ChronoUnit.DAYS.between(dueDate.toLocalDate(), baseTime.toLocalDate());
        if (overdueDays < 0L) {
            return 0;
        }

        if (overdueDays > Integer.MAX_VALUE || overdueDays < Integer.MIN_VALUE) {
            throw new IllegalArgumentException("연체가 너무 오래됐습니다.");
        }
        return overdueDays.intValue();
    }

    public Long returnBook(LocalDateTime returnDate) {
        if (returnDate.isBefore(this.loanDate)) {
            throw new IllegalArgumentException("반납일시가 대여일시보다 빠를수없습니다.");
        }
        this.returnDate = returnDate;

        updateStock();

        return OverdueFeeCalculator.calculateOverdueFee(this.getOverdueDays(returnDate));
    }

    private void updateStock() {
        if (this.book.getStatus() == BookStatus.OUT_OF_STOCK) {
            this.book.changeStatus(BookStatus.IN_STOCK);
        }
    }
}
