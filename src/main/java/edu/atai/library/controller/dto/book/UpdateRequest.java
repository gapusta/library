package edu.atai.library.controller.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotNull
    private Integer quantity;

}
