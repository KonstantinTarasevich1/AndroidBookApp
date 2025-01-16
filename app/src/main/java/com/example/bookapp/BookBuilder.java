package com.example.bookapp;

import com.example.bookapp.models.Book;

public abstract class BookBuilder {

    public static Book Build(int id, String Name, String Author,
                             String AddressBought, String Year){
        Book b = new Book();
        b.setId(id);
        b.setName(Name);
        b.setAuthor(Author);
        b.setAddressBought(AddressBought);
        b.setYear(Year);
        return b;
    }

}
