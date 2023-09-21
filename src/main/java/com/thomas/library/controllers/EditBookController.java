package com.thomas.library.controllers;

import com.thomas.library.BookService;
import com.thomas.library.controllers.AddBookController;
import com.thomas.library.models.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EditBookController {

    private final BookService bookService;
    private static final Logger logger = LogManager.getLogger(AddBookController.class);

    public EditBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/editBook/{id}")
    public String editBook(@PathVariable int id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "editbook";
    }

    @PostMapping("/editBook/{id}")
    public String updateBook(@ModelAttribute Book book,
                             @RequestParam("name") String name,
                             @RequestParam("author") String author) {

        book.setName(name);
        book.setAuthor(author);
        bookService.updateBook(book);
        logger.warn("The book" + book.getName() + " has been edited by " + book.getAuthor() +".");

        // Redirect to the home page
        return "redirect:/home?page=1";
    }
}
