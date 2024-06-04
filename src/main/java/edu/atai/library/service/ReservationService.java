package edu.atai.library.service;

import edu.atai.library.controller.dto.reservation.SearchRequest;
import edu.atai.library.controller.dto.reservation.SetTakenRequest;
import edu.atai.library.exception.AppException;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;
    private final BookService bookService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public Page<ReservationView> findAll(SearchRequest request, Pageable pageable) {
        if (request.getUserId() != null)
            return repository.findReservationsByUserId(request.getUserId(), pageable);

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
    public Reservation queueReservation(Long userId, Long bookId) {
        if (repository.findOpen(userId, bookId) != null) throw AppException.duplicate();

        LocalDateTime now = LocalDateTime.now();

        Book book = bookService.findById(bookId);

        if (!book.getVisible()) throw AppException.notFound();

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
    public void cancel(Long id) {
        Reservation reservation = repository.findById(id).orElseThrow(AppException::notFound);

        if (reservation.getTakenAt() != null) return;

        reservation.setCanceledAt(LocalDateTime.now());

        reserveNext(reservation.getId(), reservation.getBook().getId());
    }

    @Transactional
    public void setTaken(Long id, SetTakenRequest request) {
        Reservation reservation = repository.findById(id).orElseThrow(AppException::notFound);

        if (reservation.getReservedAt() == null) return;
        if (reservation.getCanceledAt() != null) return;

        reservation.setTakenAt(LocalDateTime.now());
        reservation.setDueDate(request.getDue());
    }

    @Transactional
    public void setSuccessfulFinish(Long id) {
        Reservation reservation = repository.findById(id).orElseThrow(AppException::notFound);

        if (reservation.getTakenAt() == null) return;
        if (reservation.getCanceledAt() != null) return;

        reservation.setReturnedAt(LocalDateTime.now());

        reserveNext(reservation.getId(), reservation.getBook().getId());
    }

    @Transactional
    public void changeDueDate(Long id, LocalDateTime newDue) {
        Reservation reservation = repository.findById(id).orElseThrow(AppException::notFound);
        LocalDateTime takenAt = reservation.getTakenAt();

        if (takenAt != null && newDue.isBefore(takenAt)) {
            throw AppException.invalidDueDateOne();
        }

        reservation.setDueDate(newDue);
    }

    private void reserveNext(Long prev, Long bookId) {
        Optional<Reservation> next = repository.findNextWaiting(prev, bookId);

        if (next.isPresent()) {
            next.get().setReservedAt(LocalDateTime.now());
        } else {
            bookService.setAvailable(bookId);
        }
    }
}
