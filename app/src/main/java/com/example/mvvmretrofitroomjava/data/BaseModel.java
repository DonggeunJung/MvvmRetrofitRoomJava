package com.example.mvvmretrofitroomjava.data;

import androidx.lifecycle.MutableLiveData;
import java.util.List;

public abstract class BaseModel {

    public abstract void reqBookList(MutableLiveData<List<Book>> ldBooks);

    public abstract List<Book> reqBookList();
}
