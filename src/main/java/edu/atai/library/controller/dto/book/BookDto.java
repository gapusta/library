package edu.atai.library.controller.dto.book;

import edu.atai.library.model.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

    private final Long id;
    private final String title;
    private final String author;
    private final Integer quantity;
    private final Integer reserved;

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.quantity = book.getQuantity();
        this.reserved = book.getReserved();
    }

}
