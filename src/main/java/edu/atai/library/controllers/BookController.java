package edu.atai.library.controllers;

import edu.atai.library.controllers.dto.IdResponse;
import edu.atai.library.controllers.dto.book.*;
import edu.atai.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public IdResponse create(@RequestBody @Valid CreateRequest request) {
        Long id = service.create(request);
        return new IdResponse(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid UpdateRequest request) {
        service.update(id, request);
    }

    // instead of deletion
    @PutMapping("/{id}/visibility")
    public void setVisible(@PathVariable Long id, @RequestBody @Valid VisibilityRequest request) {
        service.setVisible(id, request.getVisible());
    }

}
