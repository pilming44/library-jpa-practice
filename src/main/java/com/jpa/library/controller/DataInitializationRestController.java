package com.jpa.library.controller;

import com.jpa.library.service.DataInitializationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DataInitializationRestController {
    private final DataInitializationService dataInitializationService;

    @PostMapping(("/data-init"))
    public ResponseEntity<String> initializeData() {
        dataInitializationService.initializeData();
        return new ResponseEntity<>("초기 데이터가 입력됐습니다.", HttpStatus.OK);
    }
}
