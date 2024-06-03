package edu.atai.library.controller.dto.reservation;

import edu.atai.library.model.Reservation;
import lombok.Getter;

@Getter
public class CreateResponse {

    private final Long id;
    private final String status;

    public CreateResponse(Reservation reservation) {
        id = reservation.getId();
        if (reservation.getReservedAt() == null) {
            status = "QUEUED";
        } else {
            status = "RESERVED";
        }
    }

}
