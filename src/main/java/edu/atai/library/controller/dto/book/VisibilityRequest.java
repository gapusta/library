package edu.atai.library.controller.dto.book;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisibilityRequest {

    @NotNull
    private Boolean visible;

}
