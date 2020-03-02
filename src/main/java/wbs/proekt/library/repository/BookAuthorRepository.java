package wbs.proekt.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wbs.proekt.library.models.BookAuthor;

import java.util.List;
import java.util.Optional;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
    public List<BookAuthor> findAll();
    public BookAuthor save(BookAuthor bookAuthor);
    public Optional<BookAuthor> findById(Long id);
    public Optional<BookAuthor> findByBookName(String bookName);
    public Optional<BookAuthor> findByAuthorName(String authorName);
}
