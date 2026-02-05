package org.example.book_library.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRegistrationRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
