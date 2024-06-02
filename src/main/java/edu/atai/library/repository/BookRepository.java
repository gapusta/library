package edu.atai.library.repository;

import edu.atai.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.visible AND b.title LIKE concat('%', :part, '%')")
    Page<Book> findAllVisibleByTitlePart(@Param("part") String part, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.visible")
    Page<Book> findAllVisible(Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.title LIKE concat('%', :part, '%')")
    Page<Book> findAllByTitlePart(@Param("part") String part, Pageable pageable);

}
