package com.example.mvvmretrofitroomjava.data;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.mvvmretrofitroomjava.util.ApiComponent;
import com.example.mvvmretrofitroomjava.util.DaggerApiComponent;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitModel extends BaseModel {

    @Inject
    Api api;
    static RetrofitModel instance = null;
    MutableLiveData<List<Book>> ldBooks = null;

    public static RetrofitModel getInstance() {
        if(instance == null)
            instance = new RetrofitModel();
        return instance;
    }

    // Constructor
    public RetrofitModel() {
        ApiComponent component = DaggerApiComponent.builder().build();
        component.inject(this);
    }

    @Override
    public void reqBookList(MutableLiveData<List<Book>> ldBooks) {
        this.ldBooks = ldBooks;
        Call<List<Book>> call = api.getBooks();
        call.enqueue(new Callback<List<Book>>() {

            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                // When completed, get data & save
                List<Book> bookList = response.body();
                instance.ldBooks.setValue(bookList);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.d("tag", t.getMessage());
            }
        });
    }

    @Override
    public List<Book> reqBookList() {
        return null;
    }

}
