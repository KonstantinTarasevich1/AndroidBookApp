package com.example.bookapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookapp.models.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insertBook(Book book);

    @Query("SELECT * FROM books")
    List<Book> getAllBooks();

    @Query("UPDATE books SET name = :name, author = :author, year = :year, addressBought = :addressBought " +
            " WHERE id = :id")
    void updateBook(int id, String name, String author, String year, String addressBought);

    @Query("DELETE FROM books WHERE id = :id")
    void deleteBook(int id);
}
