package com.thomas.library;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.thomas.library.models.Book;
import com.thomas.library.models.Borrowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final ObjectMapper objectMapper;
    private List<Book> books;
    @Autowired
    public BookService(@Value("${pathToFile}") String pathToFile) throws IOException {
        //this.pathToFile = pathToFile;
        objectMapper = new XmlMapper();
        File xmlFile = new File(pathToFile);
        this.books = objectMapper.readValue(xmlFile, new TypeReference<List<Book>>() {});
    }

    public List<Book> getAllBooks() throws IOException {
        return this.books;
    }
    public List<Book> getBorrowedBooks() throws IOException {

        return books.stream()
                .filter(book -> !book.getBorrowed().getFirstName().isEmpty())
                .collect(Collectors.toList());
    }

    public List<Book> getFreeBooks() throws IOException {

        return books.stream()
                .filter(book -> book.getBorrowed().getFirstName().isEmpty())
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByPageAndSize(int pageNum, int pageSize) throws  IOException{
        int skipAmount = (pageNum-1) * pageSize;

        return this.books.stream()
                     .skip(skipAmount)
                     .limit(pageSize)
                     .collect(Collectors.toList());

    }

    public List<Book> getAllBorrowedBooksByPageAndSize(int pageNum, int pageSize) throws IOException {
        int skipAmount = (pageNum-1) * pageSize;
        List<Book> borrowedBooks = getBorrowedBooks();

        return borrowedBooks.stream()
                .skip(skipAmount)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Book> getAllFreeBooksByPageAndSize(int pageNum, int pageSize) throws IOException {
        int skipAmount = (pageNum-1) * pageSize;
        List<Book> freeBooks = getFreeBooks();

        return freeBooks.stream()
                .skip(skipAmount)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public void addNewBook(Book newBook) throws IOException {
        // Set the ID for the new book
        int newBookId = generateNewBookId();
        newBook.setId(newBookId);

        this.books.add(newBook);
        objectMapper.writeValue(new File("Library.xml"), this.books);
    }


    private int generateNewBookId() { // Helper method to generate a new book ID
        int maxId = this.books.stream()
                .mapToInt(Book::getId)
                .max()
                .orElse(0);
        return maxId + 1;
    }

    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean updateBook(Book editedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == editedBook.getId()) {
                books.get(i).setName(editedBook.getName());
                books.get(i).setAuthor(editedBook.getAuthor());

                try {
                    objectMapper.writeValue(new File("Library.xml"), this.books);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        return false;
    }

    public void deleteBookById(int id) {
        Optional<Book> bookToRemove = books.stream()
                                              .filter(book -> book.getId()==id)
                                              .findFirst();

        // If the book is found, remove it from the list
        if (bookToRemove.isPresent()) {
            books.remove(bookToRemove.get());

            try {
                objectMapper.writeValue(new File("Library.xml"), this.books);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean returnBookById(int id) {
        // Find the book to update in the list
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {

                books.get(i).setBorrowed(new Borrowed("", "", ""));

                try {
                    objectMapper.writeValue(new File("Library.xml"), this.books);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        return false;
    }
}

