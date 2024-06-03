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
        borrowableBook = Book.createBook("Effective Java", author, publisher, BookStatus.IN_STOCK, 1);
        unavailableBook = Book.createBook("InEffective Java", author, publisher, BookStatus.OUT_OF_STOCK, 0);
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
        assertThat(borrowableBook.getStatus()).isEqualTo(BookStatus.OUT_OF_STOCK);
    }

    @ParameterizedTest
    @CsvSource({"1, 7", "2, 7", "3, 10", "4, 10", "5, 10", "6, 14", "10, 14"})
    @DisplayName("총 보유권수에따른 대여기간 적용")
    void 대여기간_적용(int totalQuantity, int loanDays) {
        //given
        Book book = Book.createBook("Effective Java", author, publisher, BookStatus.IN_STOCK, totalQuantity);
        BookLoanForm bookLoanForm = new BookLoanForm();
        bookLoanForm.setBorrowerName("최재현");
        bookLoanForm.setLoanDate(LocalDateTime.parse("2024-05-19T15:30:00"));

        //when
        BookLoan loan = book.loan(bookLoanForm, 0);

        //then
        assertThat(loan.getDueDate()).isEqualTo(LocalDateTime.parse("2024-05-19T15:30:00").plusDays(loanDays));
    }

    @Test
    void 보유중_정상_생성() {
        //when
        Book book = Book.createBook("보유 정상 타이틀", author, publisher, BookStatus.IN_STOCK, 5);

        //then
        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo("보유 정상 타이틀");
        assertThat(book.getStatus()).isEqualTo(BookStatus.IN_STOCK);
        assertThat(book.getTotalQuantity()).isEqualTo(5);
    }

    @Test
    void 보유중_예외_발생() {
        assertThatThrownBy(() -> {
            Book.createBook("보유 예외 타이틀", author, publisher, BookStatus.IN_STOCK, 0);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보유중 상태일 땐 책의 수량은 0보다 커야 합니다.");
    }

    @Test
    void 미보유_정상_생성() {
        //when
        Book book = Book.createBook("미보유 정상 타이틀", author, publisher, BookStatus.OUT_OF_STOCK, 0);

        //then
        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo("미보유 정상 타이틀");
        assertThat(book.getStatus()).isEqualTo(BookStatus.OUT_OF_STOCK);
        assertThat(book.getTotalQuantity()).isEqualTo(0);
    }

    @Test
    void 미보유_예외_발생() {
        assertThatThrownBy(() -> {
            Book.createBook("미보유 예외 타이틀", author, publisher, BookStatus.OUT_OF_STOCK, 5);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("미보유 상태일 땐 책의 수량은 0이어야합니다.");
    }
}