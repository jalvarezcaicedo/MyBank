package com.androidapp.test.mybank.util;

import android.content.Context;
import android.content.SharedPreferences;


public class SingletonSharedPreference {
    private static SingletonSharedPreference sSharedPrefs;
    private SharedPreferences mPref;


    private SingletonSharedPreference(Context context) {
        mPref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }


    public static SingletonSharedPreference getInstance(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new SingletonSharedPreference(context.getApplicationContext());
        }
        return sSharedPrefs;
    }

    public static SingletonSharedPreference getInstance() {
        if (sSharedPrefs != null) {
            return sSharedPrefs;
        }

        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");
    }

    public SharedPreferences getSharedPrefs() {
        return mPref;
    }

    public SharedPreferences.Editor getEditorPrefs() {
        return mPref.edit();
    }
}
