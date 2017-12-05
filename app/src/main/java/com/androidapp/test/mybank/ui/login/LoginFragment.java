package com.androidapp.test.mybank.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidapp.test.mybank.R;
import com.androidapp.test.mybank.data.model.login.Login;
import com.androidapp.test.mybank.ui.base.BaseFragment;
import com.androidapp.test.mybank.ui.menu.MenuFragment;
import com.androidapp.test.mybank.ui.register.RegisterFragment;
import com.androidapp.test.mybank.util.Constants;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class LoginFragment extends BaseFragment implements LoginView, View.OnClickListener {

    public static final String FRAGMENT_TAG = "FRAGMENT_LOGIN";
    private LoginPresenter loginPresenter;
    private EditText customerId;
    private EditText password;
    private ConstraintLayout containerLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initUI(view);
        setHasOptionsMenu(true);
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(LoginFragment.this);

        return view;
    }

    private void initUI(View view) {
        clearUserSharedPreference();
        //getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit().putString(Constants.SERVER, "192.168.0.11").apply();
        containerLogin = view.findViewById(R.id.container_login);
        customerId = view.findViewById(R.id.field_customer_id);
        password = view.findViewById(R.id.field_password);
        TextView linkSignUp = view.findViewById(R.id.link_sign_up);
        SpannableString content = new SpannableString(getString(R.string.link_sign_up));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        linkSignUp.setText(content);
        linkSignUp.setOnClickListener(this);

        //TODO remove dummy data on finish
        setDummyData();

        Button btnLogin = view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(this);
    }

    private void setDummyData() {
        customerId.setText("1234574");
        password.setText("1234574");
    }

    private void clearUserSharedPreference() {
        SharedPreferences.Editor sharedPreferencesEditor = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();

        sharedPreferencesEditor.putString(Constants.AUTHORIZATION_HEADER, "");
        sharedPreferencesEditor.putString(Constants.CUSTOMER_ID, "");
        sharedPreferencesEditor.apply();
    }

    @Override
    public void sendToMenu() {
        Fragment fragment = new MenuFragment();

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, MenuFragment.FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(MenuFragment.FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void showSnackBar(String msg) {
        Snackbar.make(containerLogin, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void getCustomerInfo() {
        try {
            loginPresenter.callCustomerInfo();
        } catch (CertificateException | NoSuchAlgorithmException | IOException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private void sendToRegister() {
        Fragment fragment = new RegisterFragment();

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, RegisterFragment.FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(RegisterFragment.FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_login, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_net_prefs:

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Net preferences");
                alertDialog.setMessage("Enter your server direction");

                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Save",
                        (dialog, which) -> {
                            if (!TextUtils.isEmpty(input.getText().toString())) {
                                getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit()
                                        .putString(Constants.SERVER, input.getText().toString()).apply();
                            } else {
                                dialog.cancel();
                            }
                        });

                alertDialog.setNegativeButton("Cancel",
                        (dialog, which) -> dialog.cancel());

                alertDialog.show();

                break;
            default:
                Log.i(getClass().getSimpleName(), "No se reconoce la opcion indicada");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Login login = new Login();
                login.setCustomerId(customerId.getText().toString());
                login.setPassword(password.getText().toString());
                try {
                    loginPresenter.callServiceLogin(login);
                } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | KeyManagementException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.link_sign_up:
                sendToRegister();
                break;
        }
    }

}
