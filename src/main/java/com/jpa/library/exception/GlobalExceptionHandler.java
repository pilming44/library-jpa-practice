package com.jpa.library.exception;

import com.jpa.library.dto.ErrorResult;
import com.jpa.library.dto.ResultWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResultWrapper> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ErrorResult errorResult = new ErrorResult(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage() + ": EntityNotFoundException",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(new ResultWrapper<>(errorResult), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResultWrapper> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorResult errorResult = new ErrorResult(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage() + ": IllegalArgumentException",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(new ResultWrapper<>(errorResult), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultWrapper> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        String messages = ex.getBindingResult().getAllErrors().stream()
                .map(error -> (error).getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResult errorResult = new ErrorResult(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                messages + ": MethodArgumentNotValidException",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(new ResultWrapper<>(errorResult), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BorrowException.class)
    public ResponseEntity<ResultWrapper> handleBorrowException(BorrowException ex, WebRequest request) {
        ErrorResult errorResult = new ErrorResult(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage() + ": BorrowException",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(new ResultWrapper<>(errorResult), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ResultWrapper> handleDuplicateException(DuplicateException ex, WebRequest request) {
        ErrorResult errorResult = new ErrorResult(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage() + ": DuplicateException",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(new ResultWrapper<>(errorResult), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataInitializationException.class)
    public ResponseEntity<ResultWrapper> handleDataInitializationException(DataInitializationException ex, WebRequest request) {
        ErrorResult errorResult = new ErrorResult(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage() + ": DataInitializationException",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(new ResultWrapper<>(errorResult), HttpStatus.CONFLICT);
    }
}
