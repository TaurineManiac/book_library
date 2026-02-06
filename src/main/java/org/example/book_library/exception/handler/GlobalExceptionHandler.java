package org.example.book_library.exception.handler;

import jakarta.persistence.EntityNotFoundException;
import org.example.book_library.dto.response.AppErrorResponse;
import org.example.book_library.exception.custom.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        final HttpStatus status = HttpStatus.BAD_REQUEST;

        e.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(status).body(AppErrorResponse.builder()
                .errors(errors)
                .status(status.value())
                .message(status.getReasonPhrase())
                .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppErrorResponse> handleNotFoundException(EntityNotFoundException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("id", e.getMessage());
        final HttpStatus status = HttpStatus.NOT_FOUND; //404
        return ResponseEntity.status(status).body(AppErrorResponse.builder()
                .status(404)
                .message("Not found right element.")
                .errors(errors)
                .build());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<AppErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("id", e.getMessage());
        final HttpStatus status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(AppErrorResponse.builder()
                .status(status.value())
                .message("User already exists. Registration error!")
                .errors(errors)
                .build());
    }
}
