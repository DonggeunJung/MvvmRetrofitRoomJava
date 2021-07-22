package com.example.mvvmretrofitroomjava.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase {

    public abstract BookDao dao();

    private static volatile BookDatabase instance = null;

    public static BookDatabase getInstance(Context context) {
        if(instance == null) {
            //synchronized (BookDatabase.class) {
                instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        BookDatabase.class, "book_database")
                        .build();
            //}
        }
        return instance;
    }
}
