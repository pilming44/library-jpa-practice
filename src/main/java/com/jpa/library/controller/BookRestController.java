package com.jpa.library.controller;

import com.jpa.library.dto.*;
import com.jpa.library.service.BookLoanService;
import com.jpa.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookRestController {
    private final BookService bookService;
    private final BookLoanService bookLoanService;

    @GetMapping("/book/summary")
    public ResponseEntity<ResultWrapper> getBookSummary(@Validated BookSearchForm bookSearchForm) {
        return new ResponseEntity<>(bookService.findBookSummaryList(bookSearchForm), HttpStatus.OK);
    }

    @PostMapping("/book/add")
    public ResponseEntity<ResultWrapper> addBook(@Validated BookForm bookForm) {
        Long bookId = bookService.save(bookForm);
        return new ResponseEntity(bookService.findBookInfo(bookId), HttpStatus.CREATED);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<ResultWrapper> bookDetail(@PathVariable("id") Long bookId) {
        return new ResponseEntity(bookService.findBookDeatil(bookId), HttpStatus.OK);
    }

    @PostMapping("/book/loan")
    public ResponseEntity<ResultWrapper> addBookLoan(@Validated BookLoanForm bookLoanForm) {
        return new ResponseEntity(new ResultWrapper<>(bookLoanService.loan(bookLoanForm)), HttpStatus.CREATED);
    }

    @PatchMapping("/book/return")
    public ResponseEntity<ResultWrapper> returnBook(@Validated BookReturnForm bookReturnForm) {
        return new ResponseEntity(bookService.returnBook(bookReturnForm), HttpStatus.OK);
    }
}
