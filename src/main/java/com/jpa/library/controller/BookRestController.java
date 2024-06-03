package com.jpa.library.controller;

import com.jpa.library.dto.BookForm;
import com.jpa.library.dto.BookLoanForm;
import com.jpa.library.dto.BookSearchForm;
import com.jpa.library.dto.ResultWrapper;
import com.jpa.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookRestController {
    private final BookService bookService;

    @GetMapping("/book/summary")
    public ResponseEntity<ResultWrapper> getBookSummary(@Validated BookSearchForm bookSearchForm) {
        return new ResponseEntity<>(bookService.findBookSummaryList(bookSearchForm), HttpStatus.OK);
    }

    @PostMapping("/book/add")
    public ResponseEntity<ResultWrapper> addBook(@Validated BookForm bookForm) {
        Long bookId = bookService.save(bookForm);
        return new ResponseEntity(bookService.findBookInfo(bookId), HttpStatus.CREATED);
    }
}
