package edu.atai.library.controller.dto.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SetTakenRequest {

    @NotNull
    private LocalDateTime due;

}
