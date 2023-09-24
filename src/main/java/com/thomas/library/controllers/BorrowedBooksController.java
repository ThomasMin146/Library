package com.thomas.library.controllers;

import com.thomas.library.BookRepository;
import com.thomas.library.BookService;
import com.thomas.library.BorrowedRepository;
import com.thomas.library.models.Book;
import com.thomas.library.models.Borrowed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class BorrowedBooksController {
    @Value("${pageSize}")
    private int pageSize;

    private final BookService bookService;
    private final BookRepository repository;

    public BorrowedBooksController(BookService bookService, BookRepository repository) {
        this.bookService = bookService;
        this.repository = repository;
    }

    @GetMapping("/borrowedbooks")
    public String borrowedBooksPage(Model model, @RequestParam(value = "page", required = true, defaultValue = "1") int pageNum) throws IOException {
        List<Book> books = bookService.getAllBorrowedBooksByPageAndSize(pageNum, pageSize);

        // Calculate the total number of pages
        int totalBooks = bookService.getBorrowedBooks().size();
        int totalPages = (int) Math.ceil((double) totalBooks / pageSize);
        repository.getBorrowedBooks(PageRequest.of(pageNum-1, pageSize));

        model.addAttribute("books", books);
        model.addAttribute("totalPages", totalPages); // Pass totalPages to the template

        return "borrowedbooks";
    }

    @PostMapping("/returnBook/{id}")
    public String returnBook(@PathVariable int id) {
        bookService.returnBookById(id);
        Optional<Book> optionalBook = repository.findById(id);

        if(optionalBook.isPresent()){
            optionalBook.get().setBorrowed(null);
            repository.save(optionalBook.get());
        }

        // Redirect back to the borrowed books page
        return "redirect:/borrowedbooks?page=1";
    }

}
