package com.thomas.library.controllers;

import com.thomas.library.BookRepository;
import com.thomas.library.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class DeleteBookController {

    private final BookService bookService;
    private final BookRepository repository;

    public DeleteBookController(BookService bookService, BookRepository repository) {
        this.bookService = bookService;
        this.repository = repository;
    }

    @PostMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable int id) throws IOException {
        bookService.deleteBookById(id);
        repository.deleteById(id);
        return "redirect:/home";
    }
}
