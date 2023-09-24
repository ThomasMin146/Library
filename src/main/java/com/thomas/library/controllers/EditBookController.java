package com.thomas.library.controllers;

import com.thomas.library.BookRepository;
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
    private final BookRepository repository;

    public EditBookController(BookService bookService, BookRepository repository) {
        this.bookService = bookService;
        this.repository = repository;
    }

    @GetMapping("/editBook/{id}")
    public String editBook(@PathVariable int id, Model model) {
        Book book = bookService.getBookById(id);
        repository.findById(id);
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

        repository.save(book);

        logger.warn("The book" + book.getName() + " has been edited by " + book.getAuthor() +".");

        // Redirect to the home page
        return "redirect:/home?page=1";
    }
}
