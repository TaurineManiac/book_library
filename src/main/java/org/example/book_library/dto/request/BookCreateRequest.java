package org.example.book_library.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookCreateRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotNull
    @JsonFormat(pattern = "dd:MM:yyyy")
    private LocalDate releaseDate;
}
