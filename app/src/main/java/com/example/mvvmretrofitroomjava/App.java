package com.example.mvvmretrofitroomjava;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static boolean isNetConnected() {
        try {
            // Get Network connection manager
            ConnectivityManager conMgr = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // Get Network information
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

            // Check whether Network is connected
            if (netInfo != null && netInfo.isConnected())
                return true;
        } catch (Exception e) {
            Log.d("tag", "Connection state error");
        }
        return false;
    }

    // DataBinding - ImageView:imageUrl
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        // Load image from server using Glide
        Glide.with(view.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

}
