package com.jpa.library.service;

import com.jpa.library.dto.AuthorForm;
import com.jpa.library.dto.BookLoanForm;
import com.jpa.library.entity.Author;
import com.jpa.library.entity.Book;
import com.jpa.library.entity.BookLoan;
import com.jpa.library.repository.AuthorRepository;
import com.jpa.library.repository.BookLoanRepository;
import com.jpa.library.repository.BookRepository;
import com.jpa.library.util.LoanPeriodUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public void loan(BookLoanForm bookLoanForm) {
        //빌리려는 책 조회 null일수있음
        Book book = bookRepository.findById(bookLoanForm.getBookId());

        int unreturnedQuantity = bookLoanRepository.findUnreturnedBookLoansByBookId(bookLoanForm.getBookId()).size();

        bookLoanRepository.save(book.loan(bookLoanForm, unreturnedQuantity));
    }
}
