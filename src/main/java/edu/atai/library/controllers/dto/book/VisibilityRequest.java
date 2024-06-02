package edu.atai.library.controllers.dto.book;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisibilityRequest {

    @NotNull
    private Boolean visible;

}
