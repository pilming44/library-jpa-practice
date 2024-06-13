package com.jpa.library.controller;

import com.jpa.library.dto.PublisherForm;
import com.jpa.library.dto.ResultWrapper;
import com.jpa.library.service.PublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PublisherRestController {
    private final PublisherService publisherService;

    @PostMapping("/publishers")
    public ResponseEntity<ResultWrapper> addPublisher(@Validated PublisherForm publisherForm) {
        return new ResponseEntity(new ResultWrapper<>(publisherService.save(publisherForm)), HttpStatus.CREATED);
    }
}
