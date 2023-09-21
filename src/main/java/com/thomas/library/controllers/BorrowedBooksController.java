package com.thomas.library.controllers;

import com.thomas.library.BookService;
import com.thomas.library.models.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class BorrowedBooksController {
    @Value("${pageSize}")
    private int pageSize;

    private final BookService bookService;

    public BorrowedBooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/borrowedbooks")
    public String borrowedBooksPage(Model model, @RequestParam(value = "page", required = true, defaultValue = "1") int pageNum) throws IOException {
        List<Book> books = bookService.getAllBorrowedBooksByPageAndSize(pageNum, pageSize);

        // Calculate the total number of pages
        int totalBooks = bookService.getBorrowedBooks().size();
        int totalPages = (int) Math.ceil((double) totalBooks / pageSize);

        model.addAttribute("books", books);
        model.addAttribute("totalPages", totalPages); // Pass totalPages to the template

        return "borrowedbooks";
    }

    @PostMapping("/returnBook/{id}")
    public String returnBook(@PathVariable int id) {
        bookService.returnBookById(id);

        // Redirect back to the borrowed books page
        return "redirect:/borrowedbooks?page=1";
    }

}
