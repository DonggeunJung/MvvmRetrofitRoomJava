package com.example.mvvmretrofitroomjava.ui;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.mvvmretrofitroomjava.data.Book;
import com.example.mvvmretrofitroomjava.data.Repository;
import java.util.List;

public class BookViewModel extends ViewModel {
    Repository repository = null;

    public BookViewModel(Repository repository) {
        this.repository = repository;
    }

    // Return Book data list
    public LiveData<List<Book>> getBooks() {
        return repository.getBooks();
    }

    // Request Card data list to Repository
    public void reqBooks(FragmentActivity viewOwner) {
        repository.reqBooks(viewOwner);
    }
}
