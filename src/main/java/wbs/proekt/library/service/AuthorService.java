package wbs.proekt.library.service;

import wbs.proekt.library.models.Author;
import wbs.proekt.library.models.Book;
import wbs.proekt.library.models.BookAuthor;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    public List<Author> findAll();
    public Author save(Author author);
    public Optional<Author> findById(Long id);
    public Optional<Author> findByAuthorLink(String link);

    public Author getAuthorDetailsSPARQL(String link);
    public Author getAuthorDetailsNAME(String name);
    public List<BookAuthor> getBooksByAuthor(String author);
    public List<BookAuthor> getBooksDescByAuthor(String author);
}
