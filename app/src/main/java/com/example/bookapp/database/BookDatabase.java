package com.example.bookapp.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.bookapp.models.Book;

@Database(entities = {Book.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase {

    private static BookDatabase instance;

    public abstract BookDao bookDao();

    public static synchronized BookDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            BookDatabase.class, "book_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}