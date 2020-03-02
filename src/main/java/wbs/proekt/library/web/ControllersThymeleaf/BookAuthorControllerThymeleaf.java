package wbs.proekt.library.web.ControllersThymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wbs.proekt.library.models.BookAuthor;
import wbs.proekt.library.service.BookAuthorService;

import java.util.List;

@Controller
public class BookAuthorControllerThymeleaf {

    private BookAuthorService bookAuthorService;

    public BookAuthorControllerThymeleaf(BookAuthorService bookAuthorService) {
        this.bookAuthorService = bookAuthorService;
    }

    @GetMapping
    public String listAll(Model model){
        model.addAttribute("list", bookAuthorService.getAllSPARQL());
        return "index";
    }

    @GetMapping("/search/book")
    public String listAllWithBookName(@RequestParam String bookName, @RequestParam String order, Model model) {
        if (order.equals("asc")){
            model.addAttribute("list", bookAuthorService.getWithBookName(bookName));
        }
        else{
            model.addAttribute("list", bookAuthorService.getWithBookNameDesc(bookName));
        }

        return "index";
    }


//    @GetMapping("/search/desc/book")
//    public String listAllWithBookNameDesc(@RequestParam String bookName, Model model) {
//        model.addAttribute("list", bookAuthorService.getWithBookNameDesc(bookName));
//        return "index";
//    }

    @GetMapping("/search/author")
    public String listAllWithAuthorName(@RequestParam String authorName, @RequestParam String order, Model model) {
        if (order.equals("asc")){
            model.addAttribute("list", bookAuthorService.getWithAuthorName(authorName));
        }
        else{
            model.addAttribute("list", bookAuthorService.getWithAuthorNameDesc(authorName));
        }

        return "index";
    }


//    @GetMapping("/search/desc/author")
//    public String listAllWithAuthorNameDesc(@RequestParam String authorName, Model model) {
//        model.addAttribute("list", bookAuthorService.getWithAuthorNameDesc(authorName));
//        return "index";
//    }



}
