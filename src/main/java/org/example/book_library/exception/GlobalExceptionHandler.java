package org.example.book_library.exception;

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
    public ResponseEntity<BookErrorResponse> handleException(MethodArgumentNotValidException e) {
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
}
