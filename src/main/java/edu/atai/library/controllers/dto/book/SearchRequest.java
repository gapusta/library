package edu.atai.library.controllers.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {

    private Long bookId;

    private String name;

}
