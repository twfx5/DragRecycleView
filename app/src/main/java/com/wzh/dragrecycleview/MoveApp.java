package com.wzh.dragrecycleview;

import android.app.Application;

public class MoveApp extends Application {

    public static MoveApp mInstance;

    public static MoveApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
