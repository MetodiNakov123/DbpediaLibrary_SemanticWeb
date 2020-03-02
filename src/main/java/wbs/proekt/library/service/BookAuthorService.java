package wbs.proekt.library.service;

import wbs.proekt.library.models.BookAuthor;

import java.util.List;
import java.util.Optional;

public interface BookAuthorService {
    public List<BookAuthor> findAll();
    public BookAuthor save(BookAuthor bookAuthor);
    public Optional<BookAuthor> findById(Long id);
    public Optional<BookAuthor> findByBookName(String bookName);
    public Optional<BookAuthor> findByAuthorName(String authorName);

    public List<BookAuthor> getAllSPARQL();
    public List<BookAuthor> getWithBookName(String bookName);
    public List<BookAuthor> getWithBookNameDesc(String bookName);
    public List<BookAuthor> getWithAuthorName(String authorName);
    public List<BookAuthor> getWithAuthorNameDesc(String authorName);
}
