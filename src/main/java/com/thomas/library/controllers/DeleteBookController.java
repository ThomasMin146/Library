package com.thomas.library.controllers;

import com.thomas.library.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class DeleteBookController {

    private final BookService bookService;

    public DeleteBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable int id) throws IOException {
        bookService.deleteBookById(id);
        return "redirect:/home";
    }
}
