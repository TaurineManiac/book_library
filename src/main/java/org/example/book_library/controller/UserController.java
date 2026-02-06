package org.example.book_library.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.book_library.domain.User;
import org.example.book_library.dto.request.UserRegistrationRequest;
import org.example.book_library.dto.response.UserRegistrationResponse;
import org.example.book_library.service.inter.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
        return ResponseEntity.ok(authService.register(userRegistrationRequest));
    }
}
