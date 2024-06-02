package edu.atai.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "reserved_at")
    private LocalDateTime reservedAt;

    @Column(name = "taken_at")
    private LocalDateTime takenAt;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

}
