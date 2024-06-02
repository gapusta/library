package edu.atai.library.controllers.dto.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReservationRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long bookId;

}
