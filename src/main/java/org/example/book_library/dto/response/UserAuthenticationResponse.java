package org.example.book_library.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthenticationResponse {
    private String username;
    private String jwtToken;
}
