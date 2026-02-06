package org.example.book_library.service.inter;

import org.example.book_library.dto.request.UserRegistrationRequest;
import org.example.book_library.dto.response.UserRegistrationResponse;

public interface AuthService {
    public UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest);
}
