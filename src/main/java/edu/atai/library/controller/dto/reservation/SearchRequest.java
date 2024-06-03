package edu.atai.library.controller.dto.reservation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {

    private Long userId;
    private String userName;
    private String bookTitle;

}
