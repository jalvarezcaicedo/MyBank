package com.androidapp.test.mybank.ui.container;

import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.androidapp.test.mybank.R;
import com.androidapp.test.mybank.ui.base.BaseActivity;
import com.androidapp.test.mybank.ui.login.LoginFragment;


public class ContainerActivity extends BaseActivity implements ContainerView {

    ContainerPresenter containerPresenter;
    ConstraintLayout containerActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        initUI();

        containerPresenter = new ContainerPresenter();
        containerPresenter.attachView(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new LoginFragment(), LoginFragment.FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(LoginFragment.FRAGMENT_TAG);
        fragmentTransaction.commit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getSupportFragmentManager().getFragments().contains(LoginFragment.class);
        }

    }

    private void initUI() {
        containerActivity = findViewById(R.id.container_activity);
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(containerActivity, message, Snackbar.LENGTH_SHORT).show();
    }
}
