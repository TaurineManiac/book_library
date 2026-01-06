package org.example.book_library.dto.response;


import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookResponse {
    private Long  id;
    private String title;
    private String author;
    private LocalDate releaseDate;
    private Boolean success;
    private String message;
}
