package com.jpa.library.service;

import com.jpa.library.dto.BookLoanForm;
import com.jpa.library.dto.BookLoanResult;
import com.jpa.library.entity.Book;
import com.jpa.library.entity.BookLoan;
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

        int unreturnedQuantity = bookLoanRepository.findUnreturnedBookLoansByBookId(bookLoanForm.getBookId()).size();

        BookLoan loan = book.loan(bookLoanForm, unreturnedQuantity);

        bookLoanRepository.save(loan);

        return new BookLoanResult(loan.getBook().getTitle(), loan.getBorrowerName(), loan.getLoanDate(), loan.getDueDate());
    }
}
