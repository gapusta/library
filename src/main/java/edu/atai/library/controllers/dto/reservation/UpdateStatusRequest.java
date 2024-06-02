package edu.atai.library.controllers.dto.reservation;

import jakarta.validation.constraints.NotBlank;

public class UpdateStatusRequest {

    // "TAKEN" - student took a book
    // "FINISHED" - student returned a book (+ give book to other reservation)
    @NotBlank
    private final String status;

    public UpdateStatusRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}