package edu.atai.library.controllers.dto.reservation;

import edu.atai.library.model.Reservation;
import lombok.Getter;

@Getter
public class CreateReservationResponse {

    private final String status;

    public CreateReservationResponse(Reservation reservation) {
        if (reservation.getReservedAt() == null) {
            status = "QUEUED";
        } else {
            status = "RESERVED";
        }
    }

}
