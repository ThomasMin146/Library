package com.thomas.library;

import com.thomas.library.models.Book;
import com.thomas.library.models.Borrowed;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    // Add your test methods here
    @Test
    public void testFindFreeBooks() {
        // Arrange: Create some test data with free books
        Book freeBook1 = new Book();
        freeBook1.setName("name");
        freeBook1.setBorrowed(new Borrowed("", "", "")); // Set the Borrowed entity to null for free book
        // Add other properties for freeBook1 and save it
        bookRepository.save(freeBook1);

        Book freeBook2 = new Book();
        freeBook2.setBorrowed(null); // Set the Borrowed entity to null for free book
        // Add other properties for freeBook2 and save it
        bookRepository.save(freeBook2);

        Book borrowedBook = new Book();
        borrowedBook.setBorrowed(new Borrowed("John", "", ""));
        // Add other properties for borrowedBook and save it
        bookRepository.save(borrowedBook);

        assertEquals("name", bookRepository.findById(1).get().getName());

        freeBook1.setName("newname");
        bookRepository.save(freeBook1);
        assertEquals("newname", bookRepository.findById(1).get().getName());

        // Assert: Check if the list contains only free books
        assertEquals(1, bookRepository.getBorrowedBooks(PageRequest.of(0,5)).size());
        assertEquals(2, bookRepository.getFreeBooks().size());

    }
}
