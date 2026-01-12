package org.example.book_library.exception;

import jakarta.persistence.EntityNotFoundException;
import org.example.book_library.dto.response.BookErrorResponse;
import org.example.book_library.dto.response.BookResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BookErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        final HttpStatus status = HttpStatus.BAD_REQUEST;

        e.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(status).body(BookErrorResponse.builder()
                .errors(errors)
                .status(status.value())
                .message(status.getReasonPhrase())
                .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BookErrorResponse> handleNotFoundException(EntityNotFoundException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("id", e.getMessage());
        final HttpStatus status = HttpStatus.NOT_FOUND; //404
        return ResponseEntity.status(status).body(BookErrorResponse.builder()
                .status(404)
                .message("Not found right element.")
                .errors(errors)
                .build());
    }
}
