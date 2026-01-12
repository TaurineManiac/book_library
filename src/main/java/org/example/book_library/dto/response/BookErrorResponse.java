package org.example.book_library.dto.response;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookErrorResponse {
    private String message;
    private Map<String,String> errors;
    private Integer status;
}
