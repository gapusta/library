package edu.atai.library.controllers.dto.reservation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchReservationsRequest {

    private String userName;
    private String bookTitle;

}
