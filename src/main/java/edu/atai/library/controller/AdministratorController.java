package edu.atai.library.controller;

import edu.atai.library.controller.dto.IdResponse;
import edu.atai.library.controller.dto.book.*;
import edu.atai.library.controller.dto.reservation.SearchRequest;
import edu.atai.library.controller.dto.reservation.SetTakenRequest;
import edu.atai.library.model.view.ReservationView;
import edu.atai.library.service.BookService;
import edu.atai.library.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdministratorController {

    private final BookService bookService;
    private final ReservationService reservationService;

    // admin book operations

    @GetMapping("/book")
    public Page<BookDto> findAllBooks(
        @RequestBody @Valid edu.atai.library.controller.dto.book.SearchRequest request,
        @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return bookService.findAll(request, pageable).map(BookDto::new);
    }

    @PostMapping("/book")
    public IdResponse createBook(@RequestBody @Valid CreateRequest request) {
        Long id = bookService.create(request);
        return new IdResponse(id);
    }

    @PutMapping("/book/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody @Valid UpdateRequest request) {
        bookService.update(id, request);
    }

    @PutMapping("/book/{id}/visibility")
    public void setBookVisible(@PathVariable Long id, @RequestBody @Valid VisibilityRequest request) {
        bookService.setVisible(id, request.getVisible());
    }

    // admin reservation operations

    @GetMapping("/reservation")
    public Page<ReservationView> findReservations(
        @RequestBody @Valid SearchRequest request,
        @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return reservationService.findAll(request, pageable);
    }

    @PutMapping("/reservation/{id}/taken")
    public void setReservationTaken(
        @PathVariable Long id,
        @RequestBody @Valid SetTakenRequest request
    ) {
        reservationService.setTaken(id, request);
    }

    @PutMapping("/reservation/{id}/finish")
    public void setReservationFinished(@PathVariable Long id) {
        reservationService.setSuccessfulFinish(id);
    }

    @PutMapping("/reservation/{id}/due")
    public void updateReservationDue(@PathVariable Long id, @RequestBody @Valid SetTakenRequest request) {
        reservationService.changeDueDate(id, request.getDue());
    }

}
