package com.thomas.library.controllers;

import com.thomas.library.BookService;
import com.thomas.library.models.Book;
import com.thomas.library.models.Borrowed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AddBookController {

    private static final Logger logger = LogManager.getLogger(AddBookController.class);
    private final BookService bookService;

    public AddBookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/addbook")
    public String addBook() throws IOException {
        return "addbook";
    }

    @PostMapping("/addbook")
    public String addBook(
            @RequestParam("name") String name,
            @RequestParam("author") String author
    ) {
        // Create a new Book object with the provided name and author
        Book newBook = new Book();
        newBook.setName(name);
        newBook.setAuthor(author);
        newBook.setBorrowed(new Borrowed("","",""));

        try {
            // Add the new book to library (XML file)
            bookService.addNewBook(newBook);
            logger.warn("New book " + newBook.getName() + " has been created by " + newBook.getAuthor() + ".");
            return "redirect:/home?page=1";
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("New book could not been created...");
            return "error";
        }
    }
}
