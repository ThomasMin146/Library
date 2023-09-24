package com.thomas.library.controllers;

import com.thomas.library.BookService;
import com.thomas.library.BorrowedRepository;
import com.thomas.library.models.Book;
import com.thomas.library.models.Borrowed;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BorrowBookController {
    private final BookService bookService;
    private final BorrowedRepository repository;

    public BorrowBookController(BookService bookService, BorrowedRepository repository) {
        this.bookService = bookService;
        this.repository = repository;
    }

    @GetMapping("/borrow/{id}")
    public String confirmBorrow(@PathVariable int id, Model model) {
        // Retrieve the book by its ID
        Book book = bookService.getBookById(id);
        repository.findById((long) id);

        // Add the book to the model
        model.addAttribute("book", book);

        return "borrowBook";
    }

    @PostMapping("/borrow/{id}")
    public String submitBorrow(
            @PathVariable int id,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("from") String from
    ) {
        Book book = bookService.getBookById(id);
        // Set the book's status as borrowed
        book.setBorrowed(new Borrowed("", "", ""));

        book.getBorrowed().setFirstName(firstName);
        book.getBorrowed().setLastName(lastName);
        book.getBorrowed().setFrom(from);
        bookService.updateBook(book);

        repository.save(book.getBorrowed());

        // Redirect back to the free books page
        return "redirect:/freebooks?page=1";
    }
}
