package com.thomas.library.controllers;

import com.thomas.library.BookService;
import com.thomas.library.models.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {
    @Value("${pageSize}")
    private int pageSize;

    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/home")
    public String homePage(Model model, @RequestParam(value = "page", required = true, defaultValue = "1") int pageNum) throws IOException {
        List<Book> books = bookService.getBooksByPageAndSize(pageNum, pageSize);

        int totalBooks = bookService.getAllBooks().size(); // Get the total number of books
        int totalPages = (int) Math.ceil((double) totalBooks / pageSize);

        model.addAttribute("books", books);
        model.addAttribute("totalPages", totalPages); // Pass totalPages to the template

        return "home";
    }

}
