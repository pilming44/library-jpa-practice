package com.jpa.library.entity;

import com.jpa.library.dto.BookLoanForm;
import com.jpa.library.enums.BookStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class BookTest {

    private Author author;
    private Publisher publisher;
    private Book borrowableBook;
    private Book unavailableBook;

    @BeforeEach
    public void setUp() {
        author = new Author("Joshua Bloch");
        publisher = new Publisher("Addison-Wesley");
        borrowableBook = new Book("Effective Java", author, publisher, BookStatus.AVAILABLE, 1);
        unavailableBook = new Book("InEffective Java", author, publisher, BookStatus.UNAVAILABLE, 5);
    }

    @Test
    @DisplayName("대여 불가능한 책 대여시 예외 발생")
    void 대여_불가능() {
        // given
        BookLoanForm bookLoanForm = new BookLoanForm();
        bookLoanForm.setBorrowerName("최재현");
        bookLoanForm.setLoanDate(LocalDateTime.parse("2024-05-19T15:30:00"));

        assertThatThrownBy(() -> unavailableBook.loan(bookLoanForm, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("대여 불가능한 책입니다.");
    }

    @Test
    @DisplayName("더이상 대여할수있는 책이 없다면 책의 대여 가능여부를 대여 불가능으로 변경")
    void 대여상태_변경() {
        //given
        BookLoanForm bookLoanForm = new BookLoanForm();
        bookLoanForm.setBorrowerName("최재현");
        bookLoanForm.setLoanDate(LocalDateTime.parse("2024-05-19T15:30:00"));

        //when
        borrowableBook.loan(bookLoanForm, 0);

        //then
        assertThat(borrowableBook.getStatus()).isEqualTo(BookStatus.UNAVAILABLE);
    }

    @ParameterizedTest
    @CsvSource({"1, 7", "2, 7", "3, 10", "4, 10", "5, 10", "6, 14", "10, 14"})
    @DisplayName("총 보유권수에따른 대여기간 적용")
    void 대여기간_적용(int totalQuantity, int loanDays) {
        //given
        Book book = new Book("Effective Java", author, publisher, BookStatus.AVAILABLE, totalQuantity);
        BookLoanForm bookLoanForm = new BookLoanForm();
        bookLoanForm.setBorrowerName("최재현");
        bookLoanForm.setLoanDate(LocalDateTime.parse("2024-05-19T15:30:00"));

        //when
        BookLoan loan = book.loan(bookLoanForm, 0);

        //then
        assertThat(loan.getDueDate()).isEqualTo(LocalDateTime.parse("2024-05-19T15:30:00").plusDays(loanDays));


    }
}