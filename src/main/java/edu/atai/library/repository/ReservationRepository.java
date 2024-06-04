package edu.atai.library.repository;

import edu.atai.library.model.Reservation;
import edu.atai.library.model.view.ReservationView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
         SELECT r.id FROM Reservation r
         WHERE r.user.id = :userId AND
            r.book.id = :bookId AND
            r.returnedAt is NULL AND
            r.canceledAt is NULL
     """)
    Long findOpen(@Param("userId") Long userId, @Param("bookId") Long bookId);

    @Query("""
        SELECT r FROM Reservation r
        WHERE r.id != :prev AND
        r.book.id = :bookId AND
        r.returnedAt IS NULL AND
        r.canceledAt IS NULL
        ORDER BY r.createdAt ASC LIMIT 1
    """)
    Optional<Reservation> findNextWaiting(@Param("prev") Long prev, @Param("bookId") Long bookId);

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
