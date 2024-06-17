package com.jpa.library.service;

import com.jpa.library.aop.log.trace.Trace;
import com.jpa.library.dto.BookLoanForm;
import com.jpa.library.dto.BookLoanResult;
import com.jpa.library.entity.Book;
import com.jpa.library.entity.BookLoan;
import com.jpa.library.exception.BorrowException;
import com.jpa.library.exception.EntityNotFoundException;
import com.jpa.library.repository.BookLoanRepository;
import com.jpa.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Trace
public class BookLoanService {
    private final BookLoanRepository bookLoanRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void save() {
        bookLoanRepository.save(new BookLoan());
    }

    public List<BookLoan> findUnreturnedBookLoansByBookId(Long bookId) {
        return bookLoanRepository.findUnreturnedBookLoansByBookId(bookId);
    }

    @Transactional
    public BookLoanResult loan(BookLoanForm bookLoanForm) {
        Book book = bookRepository.findById(bookLoanForm.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookLoanForm.getBookId() + " not found"));

        checkDuplicateLoan(bookLoanForm);

        int unreturnedQuantity = bookLoanRepository.findUnreturnedBookLoansByBookId(bookLoanForm.getBookId()).size();

        BookLoan loan = book.loan(bookLoanForm, unreturnedQuantity);

        bookLoanRepository.save(loan);

        return new BookLoanResult(loan.getBook().getTitle(), loan.getBorrowerName(), loan.getLoanDate(), loan.getDueDate());
    }

    private void checkDuplicateLoan(BookLoanForm bookLoanForm) {
        boolean isExistsLoan = bookLoanRepository.existsByBookIdAndBorrowerNameAndReturnDateIsNull(bookLoanForm.getBookId(), bookLoanForm.getBorrowerName());
        if (isExistsLoan) {
            throw new BorrowException("같은 책을 중복으로 대여할수없습니다.");
        }
    }

    public List<BookLoan> findByBookIdAndReturnDateIsNull(Long bookId) {
        return bookLoanRepository.findByBookIdAndReturnDateIsNull(bookId);
    }

    public BookLoan findByBookNameAndBorrowerName(String bookName, String borrowerName) {
        BookLoan bookLoan = bookLoanRepository.findByBookNameAndBorrowerName(bookName, borrowerName)
                .orElseThrow(() -> new EntityNotFoundException("대여정보가 없습니다."));
        return bookLoan;
    }
}
