package com.example.mvvmretrofitroomjava.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    public void insert(Book book);

    @Update
    public void update(Book book);

    @Delete
    public void delete(Book book);

    @Query("SELECT * FROM Book") // Get all data in table
    public List<Book> getAll();

    @Query("DELETE FROM Book") // Delete all data in table
    public void delAll();

    @Query("SELECT COUNT(*) FROM Book") // Delete all data in table
    public int count();
}
