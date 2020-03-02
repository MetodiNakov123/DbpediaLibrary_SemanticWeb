package wbs.proekt.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wbs.proekt.library.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    public List<Author> findAll();
    public Author save(Author author);
    public Optional<Author> findById(Long id);
    public Optional<Author> findByAuthorLink(String link);
}
