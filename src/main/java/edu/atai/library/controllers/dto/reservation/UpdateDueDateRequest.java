package edu.atai.library.controllers.dto.reservation;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class UpdateDueDateRequest {

    @NotNull
    private final LocalDateTime due;

    public UpdateDueDateRequest(LocalDateTime due) {
        this.due = due;
    }

    public LocalDateTime getDue() {
        return due;
    }

}