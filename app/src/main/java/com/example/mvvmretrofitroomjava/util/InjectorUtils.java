package com.example.mvvmretrofitroomjava.util;

import com.example.mvvmretrofitroomjava.data.Repository;
import com.example.mvvmretrofitroomjava.data.RetrofitModel;
import com.example.mvvmretrofitroomjava.data.RoomModel;

public class InjectorUtils {

    private static ViewModelFactory viewModelFactory = null;

    public static ViewModelFactory provideViewModelFactory() {
        if(viewModelFactory == null) {
            Repository repository = Repository.getInstance(
                    RetrofitModel.getInstance(), RoomModel.getInstance());
            viewModelFactory = new ViewModelFactory(repository);
        }
        return viewModelFactory;
    }
}
