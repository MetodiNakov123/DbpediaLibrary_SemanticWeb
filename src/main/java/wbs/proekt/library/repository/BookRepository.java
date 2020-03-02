package wbs.proekt.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wbs.proekt.library.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    public List<Book> findAll();
    public Book save(Book book);
    public Optional<Book> findById(Long id);
    public Optional<Book> findByBookName(String name);
}
