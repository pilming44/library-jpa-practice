package com.jpa.library.controller;

import com.jpa.library.dto.AuthorForm;
import com.jpa.library.dto.ResultWrapper;
import com.jpa.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthorRestController {
    private final AuthorService authorService;

    @PostMapping("/authors")
    public ResponseEntity<ResultWrapper> addAuthor(@RequestBody @Validated AuthorForm authorForm) {
        return new ResponseEntity(new ResultWrapper<>(authorService.save(authorForm)), HttpStatus.CREATED);
    }
}
