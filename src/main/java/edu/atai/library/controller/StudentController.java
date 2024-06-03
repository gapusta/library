package edu.atai.library.controller;

import edu.atai.library.controller.dto.book.BookDto;
import edu.atai.library.controller.dto.book.SearchRequest;
import edu.atai.library.controller.dto.reservation.CreateRequest;
import edu.atai.library.controller.dto.reservation.CreateResponse;
import edu.atai.library.model.Reservation;
import edu.atai.library.model.User;
import edu.atai.library.model.view.ReservationView;
import edu.atai.library.service.BookService;
import edu.atai.library.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final BookService bookService;
    private final ReservationService reservationService;

    // student books operation

    @GetMapping("/book")
    public Page<BookDto> findVisibleBooks(
            @RequestBody @Valid SearchRequest request,
            @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return bookService.findAllVisible(request, pageable).map(BookDto::new);
    }

    // student reservation operations

    @GetMapping("/reservation")
    public Page<ReservationView> findReservations(
        Authentication authentication,
        @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        User student = (User) authentication.getPrincipal();
        return reservationService.findByUserId(student.getId(), pageable);
    }

    @PostMapping("/reservation")
    public CreateResponse createReservation(
        Authentication authentication,
        @RequestBody @Valid CreateRequest request
    ) {
        User student = (User) authentication.getPrincipal();
        Reservation result = reservationService.queueReservation(student.getId(), request.getBookId());
        return new CreateResponse(result);
    }

    @PostMapping("/reservation/{id}/cancel")
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancel(id);
    }

}
