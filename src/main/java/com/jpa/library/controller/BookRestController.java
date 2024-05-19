package com.jpa.library.controller;

import com.jpa.library.dto.ResultWrapper;
import com.jpa.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookRestController {
    private final BookService bookService;

    @GetMapping("/book/summary")
    public ResponseEntity<ResultWrapper> getBookSummary() {
        ResultWrapper resultWrapper = new ResultWrapper(bookService.findBookSummaryList());
        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }
}
