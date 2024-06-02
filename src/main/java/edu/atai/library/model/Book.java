package edu.atai.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer available;

    @Column(nullable = false)
    private Boolean visible;

    public Book(String title, String author, Integer quantity, Integer available, Boolean visible) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.available = available;
        this.visible = visible;
    }
}
