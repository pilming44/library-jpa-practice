package com.jpa.library.entity;

import com.jpa.library.enums.BookStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class BookLoanTest {

    private Author author;
    private Publisher publisher;
    private Book borrowableBook;
    private Book unavailableBook;

    @BeforeEach
    public void setUp() {
        author = new Author("Joshua Bloch");
        publisher = new Publisher("Addison-Wesley");
        borrowableBook = Book.createBook("Effective Java", author, publisher, BookStatus.IN_STOCK, 1);
        unavailableBook = Book.createBook("InEffective Java", author, publisher, BookStatus.OUT_OF_STOCK, 0);
    }

    @Test
    @DisplayName("연체일 계산")
    void 연체일_계산() {
        // given
        BookLoan loan = new BookLoan(borrowableBook, "최재현", LocalDateTime.parse("2024-05-19T15:30:00"), LocalDateTime.parse("2024-05-20T15:30:00"));
        LocalDateTime baseTime = LocalDateTime.parse("2024-05-25T15:30:00");

        // when
        Integer overdueDays = loan.getOverdueDays(baseTime);

        // then
        assertThat(overdueDays).isEqualTo(5);
    }

    @Test
    @DisplayName("연체가 아닐경우")
    void 연체_아닐경우() {
        // given
        BookLoan loan = new BookLoan(borrowableBook, "최재현", LocalDateTime.parse("2024-05-19T15:30:00"), LocalDateTime.parse("2024-05-26T15:30:00"));
        LocalDateTime baseTime = LocalDateTime.parse("2024-05-25T15:30:00");

        // when
        Integer overdueDays = loan.getOverdueDays(baseTime);

        // then
        assertThat(overdueDays).isEqualTo(0);
    }
}