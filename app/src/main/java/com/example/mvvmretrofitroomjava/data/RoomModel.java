package com.example.mvvmretrofitroomjava.data;

import androidx.lifecycle.MutableLiveData;
import com.example.mvvmretrofitroomjava.App;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomModel extends BaseModel {

    BookDao bookDao;
    static RoomModel instance = null;

    public static RoomModel getInstance() {
        if(instance == null)
            instance = new RoomModel();
        return instance;
    }

    // Constructor
    public RoomModel() {
        bookDao = BookDatabase.getInstance(App.context).dao();
    }

    // Get data from DB
    @Override
    public void reqBookList(MutableLiveData<List<Book>> ldBooks) {
        // Read data from DB
        Observable.just(bookDao)
                .subscribeOn(Schedulers.newThread())
                .map(dao -> dao.getAll())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookList -> ldBooks.setValue(bookList));
    }

    @Override
    public List<Book> reqBookList() {
        return bookDao.getAll();
    }

    // Return count of Book list
    public int getBookListCount() {
        return bookDao.count();
    }

    // Save Book list to DB
    public void setBookList(List<Book> bookList) {
        Observable.just(bookList)
                .subscribeOn(Schedulers.newThread())
                .map(list -> {
                    // Delete current data in DB
                    bookDao.delAll();
                    // Save new Card data one by one
                    for (int i = 0; i < bookList.size(); i++) {
                        bookDao.insert(bookList.get(i));
                    }
                    return bookList.size();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
