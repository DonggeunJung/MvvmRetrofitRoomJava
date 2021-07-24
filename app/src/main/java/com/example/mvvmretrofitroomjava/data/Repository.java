package com.example.mvvmretrofitroomjava.data;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Repository {

    RetrofitModel retrofitModel;
    RoomModel roomModel;
    int roomBookCount = 0;

    MutableLiveData<List<Book>> ldBooks = new MutableLiveData<>();
    static Repository instance = null;

    public static Repository getInstance(RetrofitModel retrofitModel, RoomModel roomModel) {
        if(instance == null)
            instance = new Repository(retrofitModel, roomModel);
        return instance;
    }

    Repository(RetrofitModel retrofitModel, RoomModel roomModel) {
        this.retrofitModel = retrofitModel;
        this.roomModel = roomModel;
    }

    final Observer<List<Book>> booksObserver = new Observer<List<Book>>() {
        @Override
        public void onChanged(@Nullable final List<Book> bookList) {
            if(roomBookCount < 1)
                roomModel.setBookList(bookList);
        }
    };

    // Return Card data list
    public LiveData<List<Book>> getBooks() {
        return ldBooks;
    }

    // Start getting Book list from server or DB
    public void reqBooks(FragmentActivity viewOwner) {
        ldBooks.observe(viewOwner, booksObserver);

        // Check whether Local DB has data
        Observable.just(roomModel)
                .subscribeOn(Schedulers.newThread())
                .map(room -> {
                    int count = room.getBookListCount();
                    roomBookCount = count;
                    BaseModel model = retrofitModel;
                    if(count > 0) model = roomModel;
                    model.reqBookList(ldBooks);
                    return count;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
