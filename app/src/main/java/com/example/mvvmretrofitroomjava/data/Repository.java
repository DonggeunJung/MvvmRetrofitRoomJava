package com.example.mvvmretrofitroomjava.data;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.mvvmretrofitroomjava.util.ApiComponent;
import com.example.mvvmretrofitroomjava.util.DaggerApiComponent;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    @Inject
    Api api;
    BookDao bookDao;

    MutableLiveData<List<Book>> ldBooks = new MutableLiveData<>();
    static Repository instance = null;

    public static Repository getInstance(BookDao bookDao) {
        if(instance == null)
            instance = new Repository(bookDao);
        return instance;
    }

    public Repository(BookDao bookDao) {
        this.bookDao = bookDao;
        ApiComponent component = DaggerApiComponent.builder().build();
        component.inject(this);
    }

    // Return Card data list
    public LiveData<List<Book>> getBooks() {
        return ldBooks;
    }

    // Start getting Book list from server or DB
    public void reqBooks() {
        // Check whether Local DB has data
        Observable.just(bookDao)
                .subscribeOn(Schedulers.newThread())
                .map(dao -> dao.count())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(count -> getBookList(count));
    }

    // Determine data resource - Server or Local DB
    private void getBookList(int count) {
        if(count < 1) {
            getBookListFromServer();
        } else {
            getBookListFromDb();
        }
    }

    // Get data from server
    private void getBookListFromServer() {
        Call<List<Book>> call = api.getBooks();
        call.enqueue(new Callback<List<Book>>() {

            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                // When completed, get data & save
                List<Book> bookList = response.body();
                onDataCompleted(bookList);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.d("tag", t.getMessage());
            }
        });
    }

    private void onDataCompleted(List<Book> bookList) {
        // If Card data is exist send data to listener
        if(!bookList.isEmpty()) {
            ldBooks.setValue(bookList);
            // Save Card list to DB
            Observable.just(bookList)
                    .subscribeOn(Schedulers.newThread())
                    .map(list -> cards2db(list))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        } else {
            getBookListFromDb();
        }
    }

    // Save Card list to DB
    private int cards2db(List<Book> bookList) {
        // Delete current data in DB
        bookDao.delAll();
        // Save new Card data one by one
        for(int i=0; i < bookList.size(); i++) {
            bookDao.insert(bookList.get(i));
        }
        return bookList.size();
    }

    // Get data from DB
    private void getBookListFromDb() {
        // Read data from DB
        Observable.just(bookDao)
                .subscribeOn(Schedulers.newThread())
                .map(dao -> dao.getAll())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookList -> ldBooks.setValue(bookList));
    }

}
