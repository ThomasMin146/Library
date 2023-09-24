package com.thomas.library;

import com.thomas.library.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {
    @Query("SELECT b FROM Book b WHERE b.borrowed is NOT NULL OR b.borrowed.firstName IS NULL OR b.borrowed.firstName = '' ")
    List<Book> getFreeBooks();

    @Query("SELECT b FROM Book b WHERE b.borrowed IS NULL OR b.borrowed.firstName IS NOT NULL AND b.borrowed.firstName <> '' ")
    List<Book> getBorrowedBooks();

    @Query("SELECT b FROM Book b WHERE b.borrowed is NOT NULL OR b.borrowed.firstName IS NULL OR b.borrowed.firstName = '' ")
    List<Book> getFreeBooks(PageRequest pageRequest);

    @Query("SELECT b FROM Book b WHERE b.borrowed IS NULL OR b.borrowed.firstName IS NOT NULL AND b.borrowed.firstName <> '' ")
    List<Book> getBorrowedBooks(PageRequest pageRequest);
}
