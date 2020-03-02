package wbs.proekt.library.web.ControllersThymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wbs.proekt.library.models.Author;
import wbs.proekt.library.service.AuthorService;

@Controller
@RequestMapping("/author")
public class AuthorControllerThymeleaf {
    private AuthorService authorService;

    public AuthorControllerThymeleaf(AuthorService authorService) {
        this.authorService = authorService;

    }

    @GetMapping("/details")
    public String getAuthorDetails(@RequestParam String author, Model model) {
         model.addAttribute("author",authorService.getAuthorDetailsNAME(author));
         return "author.details";
    }

    @GetMapping("/books")
    public String getBooksByAuthor(@RequestParam String author, @RequestParam String order, Model model) {
        if (order.equals("ASC")){
            model.addAttribute("books",authorService.getBooksByAuthor(author));
        }
        else{
            model.addAttribute("books",authorService.getBooksDescByAuthor(author));
        }

        return "author.books";
    }

//    @GetMapping("/books")
//    public String getBooksDescByAuthor(@RequestParam String author, Model model) {
//        model.addAttribute("author",authorService.getBooksDescByAuthor(author));
//        return "author.books";
//    }

}
