package edu.atai.library.controller.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {

    private Long bookId;

    private String bookTitle;

}
