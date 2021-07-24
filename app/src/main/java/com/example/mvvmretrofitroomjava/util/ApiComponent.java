package com.example.mvvmretrofitroomjava.util;

import com.example.mvvmretrofitroomjava.data.Api;
import com.example.mvvmretrofitroomjava.data.RetrofitModel;

import javax.inject.Singleton;
import dagger.Component;

/*
 * ApiComponent.java : Dagger interface for Retrofit object injection
 * Author : DONGGEUN JUNG (Dennis)
 * Date : Apr.16.2019
 */
@Singleton
@Component(modules = {ApiModule.class})
public interface ApiComponent {
    Api provideApi();

    void inject(RetrofitModel retrofitModel);
}
