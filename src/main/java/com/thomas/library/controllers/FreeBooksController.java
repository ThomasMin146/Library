package com.thomas.library.controllers;

import com.thomas.library.BookRepository;
import com.thomas.library.BookService;
import com.thomas.library.models.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class FreeBooksController {
    @Value("${pageSize}")
    private int pageSize;

    private final BookService bookService;
    private final BookRepository repository;

    public FreeBooksController(BookService bookService, BookRepository repository) {
        this.bookService = bookService;
        this.repository = repository;
    }

    @GetMapping("/freebooks")
    public String freeBooksPage(Model model, @RequestParam(value = "page", required = true, defaultValue = "1") int pageNum) throws IOException {
        List<Book> books = bookService.getAllFreeBooksByPageAndSize(pageNum, pageSize);

        int totalBooks = bookService.getFreeBooks().size(); // Get the total number of books
        int totalPages = (int) Math.ceil((double) totalBooks / pageSize);

        repository.getFreeBooks(PageRequest.of(pageNum-1, pageSize));

        model.addAttribute("books", books);
        model.addAttribute("totalPages", totalPages); // Pass totalPages to the template

        return "freebooks";
    }
}
