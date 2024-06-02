package edu.atai.library.controllers;

import edu.atai.library.controllers.dto.book.BookDto;
import edu.atai.library.controllers.dto.book.SearchRequest;
import edu.atai.library.controllers.dto.reservation.SearchReservationsRequest;
import edu.atai.library.model.view.ReservationView;
import edu.atai.library.service.BookService;
import edu.atai.library.service.ReservationService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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

    @GetMapping("/book")
    public Page<BookDto> findBooks(
        @RequestBody @Valid SearchRequest request,
        @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return bookService.findAll(request, pageable).map(BookDto::new);
    }


    @GetMapping("/reservation")
    public Page<ReservationView> findReservations(
        @RequestBody @Valid SearchReservationsRequest request,
        @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return reservationService.findAll(request, pageable);
    }

}
