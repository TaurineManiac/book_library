package org.example.book_library.dto.response;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AppErrorResponse {
    private String message;
    private Map<String,String> errors;
    private Integer status;
}
