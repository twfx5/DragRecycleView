package com.wzh.dragrecycleview;

import android.app.Application;

public class MovieApp extends Application {

    public static MovieApp mInstance;

    public static MovieApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
