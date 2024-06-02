package edu.atai.library.repository;

import edu.atai.library.model.Reservation;
import edu.atai.library.model.view.ReservationView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.book.id = :bookId ORDER BY r.createdAt ASC LIMIT 1")
    Reservation findNextInQueue(@Param("bookId") Long bookId);


    @Query("""
        SELECT
            new edu.atai.library.model.view.ReservationView(
                r.id,
                u.id,
                u.fullName,
                b.id,
                b.title,
                r.createdAt,
                r.reservedAt,
                r.takenAt,
                r.returnedAt,
                r.canceledAt,
                r.dueDate
            )
        FROM Reservation r
        JOIN r.book b
        JOIN r.user u
    """)
    Page<ReservationView> findReservations(Pageable pageable);


    @Query("""
        SELECT
            new edu.atai.library.model.view.ReservationView(
                r.id,
                u.id,
                u.fullName,
                b.id,
                b.title,
                r.createdAt,
                r.reservedAt,
                r.takenAt,
                r.returnedAt,
                r.canceledAt,
                r.dueDate
            )
        FROM Reservation r
        JOIN r.book b
        JOIN r.user u
        WHERE u.id = :userId
    """)
    Page<ReservationView> findReservationsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("""
        SELECT
            new edu.atai.library.model.view.ReservationView(
                r.id,
                u.id,
                u.fullName,
                b.id,
                b.title,
                r.createdAt,
                r.reservedAt,
                r.takenAt,
                r.returnedAt,
                r.canceledAt,
                r.dueDate
            )
        FROM Reservation r
        JOIN r.book b
        JOIN r.user u
        WHERE u.fullName LIKE concat('%', :name, '%')
    """)
    Page<ReservationView> findReservationByUserName(@Param("name") String name, Pageable pageable);

    @Query("""
        SELECT
            new edu.atai.library.model.view.ReservationView(
                r.id,
                u.id,
                u.fullName,
                b.id,
                b.title,
                r.createdAt,
                r.reservedAt,
                r.takenAt,
                r.returnedAt,
                r.canceledAt,
                r.dueDate
            )
        FROM Reservation r
        JOIN r.book b
        JOIN r.user u
        WHERE b.title LIKE concat('%', :title, '%')
    """)
    Page<ReservationView> findReservationsByBookTitle(@Param("title") String title, Pageable pageable);

}
