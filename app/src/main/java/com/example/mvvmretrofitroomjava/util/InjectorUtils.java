package com.example.mvvmretrofitroomjava.util;

import com.example.mvvmretrofitroomjava.App;
import com.example.mvvmretrofitroomjava.data.BookDao;
import com.example.mvvmretrofitroomjava.data.BookDatabase;
import com.example.mvvmretrofitroomjava.data.Repository;

public class InjectorUtils {

    private static ViewModelFactory viewModelFactory = null;

    public static ViewModelFactory provideViewModelFactory() {
        if(viewModelFactory == null) {
            BookDao dao = BookDatabase.getInstance(App.context).dao();
            Repository repository = Repository.getInstance(dao);
            viewModelFactory = new ViewModelFactory(repository);
        }
        return viewModelFactory;
    }
}
