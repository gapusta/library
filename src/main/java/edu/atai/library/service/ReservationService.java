package edu.atai.library.service;

import edu.atai.library.controllers.dto.reservation.SearchReservationsRequest;
import edu.atai.library.model.Book;
import edu.atai.library.model.Reservation;
import edu.atai.library.model.User;
import edu.atai.library.model.view.ReservationView;
import edu.atai.library.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;
    private final BookService bookService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public Page<ReservationView> findAll(SearchReservationsRequest request, Pageable pageable) {
        if (request.getUserName() != null)
            return repository.findReservationByUserName(request.getUserName(), pageable);

        if (request.getBookTitle() != null)
            return repository.findReservationsByBookTitle(request.getBookTitle(), pageable);

        return repository.findReservations(pageable);
    }

    @Transactional(readOnly = true)
    public Page<ReservationView> findByUserId(Long userId, Pageable pageable) {
        return repository.findReservationsByUserId(userId, pageable);
    }

    @Transactional
    public Reservation reserve(Long userId, Long bookId) {
        LocalDateTime now = LocalDateTime.now();

        Book book = bookService.findById(bookId);
        User user = userService.findById(userId);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setCreatedAt(now);

        if (bookService.reserve(bookId)) {
            reservation.setReservedAt(now);
        }

        return repository.save(reservation);
    }

    @Transactional
    public void updateStatus(Long id, String newStatus) {
        Reservation current = repository.findById(id).get();

        if ("CANCELED".equals(newStatus) && current.getTakenAt() == null) {
            current.setCanceledAt(LocalDateTime.now());
            return;
        }

        if ("TAKEN".equals(newStatus)) {
            current.setTakenAt(LocalDateTime.now());
            return;
        }

        if ("RETURNED".equals(newStatus)) {
            current.setReturnedAt(LocalDateTime.now());
        }

        Reservation next = repository.findNextInQueue(current.getBook().getId());

        if (next != null) {
            next.setReservedAt(LocalDateTime.now());
        } else {
            bookService.setAvailable(current.getBook().getId());
        }
    }

    @Transactional
    public void changeDueDate(Long id, LocalDateTime newDue) {
        Reservation current = repository.findById(id).get();
        current.setDueDate(newDue);
    }

}
