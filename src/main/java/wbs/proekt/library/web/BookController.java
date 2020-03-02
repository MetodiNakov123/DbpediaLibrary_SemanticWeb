package wbs.proekt.library.web;

import org.springframework.web.bind.annotation.*;
import wbs.proekt.library.models.Author;
import wbs.proekt.library.models.Book;
import wbs.proekt.library.service.BookService;

@RestController
@RequestMapping("list/book")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/details")
    @ResponseBody
    public Book getBookDetails(@RequestParam String bookLink) {
        return bookService.getBookDetailsSPARQL(bookLink);
    }



    // http://localhost:8080/list/book/name/details?book=Steal%20This%20Book
    @GetMapping("/name/details")
    @ResponseBody
    public Book getBookDetailsNAME(@RequestParam String book) {
        return bookService.getBookDetailsNAME(book);
    }
}


