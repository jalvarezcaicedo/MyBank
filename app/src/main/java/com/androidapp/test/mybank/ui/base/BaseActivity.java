package com.androidapp.test.mybank.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


import com.androidapp.test.mybank.R;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */
public class BaseActivity extends AppCompatActivity implements BaseView {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    public Context context;
    private long mActivityId;
    private AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = BaseActivity.this;
        mActivityId = savedInstanceState != null ? savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void createProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(BaseActivity.this);
            progressDialog.setCancelable(false);
        }
    }

    @Override
    public void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        runOnUiThread(() -> progressDialog.show());
    }

    @Override
    public void showProgressDialog(int message) {
        progressDialog.setMessage(getString(message));
        runOnUiThread(() -> progressDialog.show());
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.cancel();
        progressDialog = null;
    }

    @Override
    public void createAlertDialog() {
        alertDialog = new AlertDialog.Builder(BaseActivity.this);
        alertDialog.setCancelable(true);
    }

    @Override
    public void showAlertDialog(int message) {
        alertDialog.setMessage(message);
        showAlert();
    }

    @Override
    public void showAlertDialog(String message) {
        alertDialog.setMessage(message);
        showAlert();
    }

    private void showAlert() {
        alertDialog.setNeutralButton(R.string.accept, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            alertDialog = null;
        });
        runOnUiThread(() -> alertDialog.show());
    }

    protected void addFragment(@IdRes int containerViewId, @NonNull BaseFragment fragment, @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }

}

