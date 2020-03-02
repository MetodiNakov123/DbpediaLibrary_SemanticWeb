package wbs.proekt.library.service;

import wbs.proekt.library.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    public List<Book> findAll();
    public Book save(Book book);
    public Optional<Book> findById(Long id);
    public Optional<Book> findByBookName(String name);

    public Book getBookDetailsSPARQL(String bookLink);
    public Book getBookDetailsNAME(String bookName);
}
