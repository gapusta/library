package edu.atai.library.service;

import edu.atai.library.controllers.dto.book.CreateRequest;
import edu.atai.library.controllers.dto.book.SearchRequest;
import edu.atai.library.controllers.dto.book.UpdateRequest;
import edu.atai.library.model.Book;
import edu.atai.library.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Long create(CreateRequest request) {
        Book book = new Book(
            request.getTitle(),
            request.getAuthor(),
            request.getQuantity(),
            request.getQuantity(),
            false
        );

        Book saved = repository.save(book);

        return saved.getId();
    }

    @Transactional
    public void update(Long id, UpdateRequest request) {
        Book book = repository.findById(id).get();

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setQuantity(request.getQuantity());

        repository.save(book);
    }

    public Book findById(Long id) {
        return repository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public Page<Book> findAllVisible(SearchRequest request, Pageable pageable) {
        if (request.getName() != null) {
            return repository.findAllVisibleByTitlePart(request.getName(), pageable);
        }

        return repository.findAllVisible(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Book> findAll(SearchRequest request, Pageable pageable) {
        if (request.getBookId() != null) {
            return new PageImpl<>(repository.findAllById(List.of(request.getBookId())));
        }

        if (request.getName() != null) {
            return repository.findAllByTitlePart(request.getName(), pageable);
        }

        return repository.findAll(pageable);
    }

    @Transactional
    public void setVisible(Long id, Boolean visible) {
        Optional<Book> optional = repository.findById(id);

        if (optional.isEmpty()) return;

        Book book = optional.get();
        book.setVisible(visible);
    }

    @Transactional
    public Boolean reserve(Long id) {
        Book book = repository.findById(id).get();
        Integer available = book.getAvailable();

        if(available <= 0) return false;

        book.setAvailable(available - 1);
        return true;
    }

    @Transactional
    public void setAvailable(Long id) {
        Book book = repository.findById(id).get();

        book.setAvailable(book.getAvailable() + 1);
    }

}
