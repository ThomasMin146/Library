package com.thomas.library.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@JacksonXmlRootElement(localName = "Borrowed")
public class Borrowed {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @JacksonXmlProperty(localName = "FirstName")
    private String firstName;

    @JacksonXmlProperty(localName = "LastName")
    private String lastName;

    @Column(name = "BorrowedFrom")
    @JacksonXmlProperty(localName = "From")
    private String from;

    // Constructors, getters, and setters

    public Borrowed(){

    }

    public Borrowed(String firstName, String lastName, String from) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.from = from;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


}
