package edu.atai.library.controllers;

import edu.atai.library.controllers.dto.book.BookDto;
import edu.atai.library.controllers.dto.book.SearchRequest;
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
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final BookService bookService;
    private final ReservationService reservationService;

    @GetMapping("/books")
    public Page<BookDto> findBooks(
            @RequestBody @Valid SearchRequest request,
            @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return bookService.findAllVisible(request, pageable).map(BookDto::new);
    }

    @GetMapping("/reservation")
    public Page<ReservationView> findReservations(
            @RequestBody @Valid ReservationsRequest request,
            @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return reservationService.findByUserId(request.getUserId(), pageable);
    }

    @Getter
    @Setter
    public static class ReservationsRequest {
        private Long userId;
    }

}
