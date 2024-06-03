package edu.atai.library.controller.dto.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRequest {

    @NotNull
    private Long bookId;

}
