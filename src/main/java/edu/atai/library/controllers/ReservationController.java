package edu.atai.library.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import edu.atai.library.model.Reservation;
import edu.atai.library.controllers.dto.reservation.*;
import edu.atai.library.service.ReservationService;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    // only students can use this endpoint
    @PostMapping
    public CreateReservationResponse create(@RequestBody @Valid CreateReservationRequest request) {
        Reservation result = service.reserve(request.getUserId(), request.getBookId());
        return new CreateReservationResponse(result);
    }

    // only admins can use this endpoint
    @PutMapping("/{id}/status")
    public void updateStatus(@PathVariable Long id, @RequestBody @Valid UpdateStatusRequest request) {
        service.updateStatus(id, request.getStatus());
    }

    // only admins can use this endpoint
    @PutMapping("/{id}/due")
    public void updateReservationDue(@PathVariable Long id, @RequestBody @Valid UpdateDueDateRequest request) {
        service.changeDueDate(id, request.getDue());
    }

}
