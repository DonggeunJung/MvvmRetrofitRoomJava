package com.example.mvvmretrofitroomjava.util;

import com.example.mvvmretrofitroomjava.data.Api;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/*
 * ApiComponent.java : Dagger class for Retrofit object injection
 * Author : DONGGEUN JUNG (Dennis)
 * Date : Apr.16.2019
 */
@Module
class ApiModule {
    @Provides
    @Singleton
    Api provideApi() {
        // Make Retrofit API object & return
        return Api.retrofit.create(Api.class);
    }
}
