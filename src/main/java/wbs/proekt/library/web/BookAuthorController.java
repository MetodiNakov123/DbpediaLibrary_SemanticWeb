package wbs.proekt.library.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wbs.proekt.library.models.BookAuthor;
import wbs.proekt.library.service.BookAuthorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/list")
public class BookAuthorController {

    private BookAuthorService bookAuthorService;

    public BookAuthorController(BookAuthorService bookAuthorService) {
        this.bookAuthorService = bookAuthorService;
    }

    @RequestMapping
    @ResponseBody
    public List<BookAuthor> listAll() {
        return bookAuthorService.getAllSPARQL();
    }

    @GetMapping("/search/book")
    @ResponseBody
    public List<BookAuthor> listAllWithBookName(@RequestParam String bookName) {
        return bookAuthorService.getWithBookName(bookName);
    }

    @GetMapping("/search/desc/book")
    @ResponseBody
    public List<BookAuthor> listAllWithBookNameDesc(@RequestParam String bookName) {
        return bookAuthorService.getWithBookNameDesc(bookName);
    }

    @GetMapping("/search/author")
    @ResponseBody
    public List<BookAuthor> listAllWithAuthorName(@RequestParam String authorName) {
        return bookAuthorService.getWithAuthorName(authorName);
    }

    @GetMapping("/search/desc/author")
    @ResponseBody
    public List<BookAuthor> listAllWithAuthorNameDesc(@RequestParam String authorName) {
        return bookAuthorService.getWithAuthorNameDesc(authorName);
    }


}
