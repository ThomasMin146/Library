package com.thomas.library.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;



@JacksonXmlRootElement(localName = "Book")
public class Book {

    @JacksonXmlProperty(localName = "id", isAttribute = true)
    private int id;

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "Author")
    private String author;

    @JacksonXmlProperty(localName = "Borrowed")
    private Borrowed borrowed;

    // Constructors, getters, and setters

    public Book(){
    }

    public Book(int id, String name, String author, Borrowed borrowed) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.borrowed = borrowed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Borrowed getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Borrowed borrowed) {
        this.borrowed = borrowed;
    }

}


