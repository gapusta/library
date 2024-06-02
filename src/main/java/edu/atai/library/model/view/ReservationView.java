package edu.atai.library.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ReservationView {

    private Long id;
    private Long studentId;
    private String studentName;
    private Long bookId;
    private String bookTitle;
    private LocalDateTime createdAt;
    private LocalDateTime reservedAt;
    private LocalDateTime takenAt;
    private LocalDateTime returnedAt;
    private LocalDateTime canceledAt;
    private LocalDateTime dueDate;

}
