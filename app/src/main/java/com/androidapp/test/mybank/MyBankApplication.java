package com.androidapp.test.mybank;

import android.app.Application;
import android.content.Context;

import com.androidapp.test.mybank.util.SingletonSharedPreference;

/**
 * Created by jhonatanalvarezcaicedo on 27/11/17.
 */

public class MyBankApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        SingletonSharedPreference.getInstance(this);
    }
}
