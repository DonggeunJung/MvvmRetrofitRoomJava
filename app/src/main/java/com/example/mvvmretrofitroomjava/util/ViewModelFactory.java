package com.example.mvvmretrofitroomjava.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.mvvmretrofitroomjava.data.Repository;
import com.example.mvvmretrofitroomjava.ui.BookViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Repository repository;

    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == BookViewModel.class) {
            return (T)new BookViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}");
    }
}
