package org.example.book_library.dto.response;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRegistrationResponse {
    private String username;
    private String password;
    private boolean success;
    private String message;
}
