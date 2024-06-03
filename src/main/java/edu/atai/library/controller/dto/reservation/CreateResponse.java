package edu.atai.library.controller.dto.reservation;

import edu.atai.library.model.Reservation;
import lombok.Getter;

@Getter
public class CreateResponse {

    private final String status;

    public CreateResponse(Reservation reservation) {
        if (reservation.getReservedAt() == null) {
            status = "QUEUED";
        } else {
            status = "RESERVED";
        }
    }

}
