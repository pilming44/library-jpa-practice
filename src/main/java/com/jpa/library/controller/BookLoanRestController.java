package com.jpa.library.controller;

import com.jpa.library.dto.BookLoanForm;
import com.jpa.library.dto.ResultWrapper;
import com.jpa.library.service.BookLoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookLoanRestController {
    private final BookLoanService bookLoanService;

    @PostMapping("/book/loan")
    public ResponseEntity<ResultWrapper> addBookLoan(BookLoanForm bookLoanForm) {
        return new ResponseEntity(new ResultWrapper<>(bookLoanService.loan(bookLoanForm)),HttpStatus.CREATED);
    }
}
