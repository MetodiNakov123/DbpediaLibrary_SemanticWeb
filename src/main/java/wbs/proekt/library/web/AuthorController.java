package wbs.proekt.library.web;

import org.springframework.web.bind.annotation.*;
import wbs.proekt.library.models.Author;
import wbs.proekt.library.models.BookAuthor;
import wbs.proekt.library.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/list/author")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;

    }

    @GetMapping("/details")
    @ResponseBody
    public Author getAuthorDetails(@RequestParam String authorLink) {
        return authorService.getAuthorDetailsSPARQL(authorLink);
    }

}
