package com.jpa.library.exception;

import com.jpa.library.dto.ErrorResult;
import com.jpa.library.dto.ResultWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResultWrapper> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ErrorResult errorResult = new ErrorResult(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(new ResultWrapper<>(errorResult), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResultWrapper> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorResult errorResult = new ErrorResult(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(new ResultWrapper<>(errorResult), HttpStatus.BAD_REQUEST);
    }
}
