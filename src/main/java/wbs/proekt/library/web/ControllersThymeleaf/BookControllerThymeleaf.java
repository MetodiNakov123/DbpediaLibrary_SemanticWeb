package wbs.proekt.library.web.ControllersThymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wbs.proekt.library.models.Book;
import wbs.proekt.library.service.BookService;

@Controller
@RequestMapping("book")
public class BookControllerThymeleaf {
    private BookService bookService;

    public BookControllerThymeleaf(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/details")
    public String getBookDetails(@RequestParam String book, Model model)
    {
        model.addAttribute("book",bookService.getBookDetailsNAME(book));
        return "book.details";
    }


}
