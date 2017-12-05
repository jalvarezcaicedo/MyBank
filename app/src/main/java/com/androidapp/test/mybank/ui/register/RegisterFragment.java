package com.androidapp.test.mybank.ui.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidapp.test.mybank.R;
import com.androidapp.test.mybank.data.model.signup.SignUpRequest;
import com.androidapp.test.mybank.ui.base.BaseFragment;
import com.androidapp.test.mybank.ui.login.LoginFragment;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class RegisterFragment extends BaseFragment implements RegisterView, View.OnClickListener, TextWatcher {

    public static final String FRAGMENT_TAG = "FRAGMENT_REGISTER";
    private RegisterPresenter registerPresenter;
    private TextInputLayout name;
    private TextInputLayout surname;
    private TextInputLayout customerId;
    private TextInputLayout email;
    private TextInputLayout mobile;
    private TextInputLayout phone;
    private TextInputLayout password;
    private Button btnSignUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initUI(view);
        registerPresenter = new RegisterPresenter();
        registerPresenter.attachView(RegisterFragment.this);

        //TODO: remove dummy data
        //setDummyData();

        return view;
    }

    private void setDummyData() {
        name.getEditText().setText("test");
        surname.getEditText().setText("test");
        customerId.getEditText().setText("123457");
        email.getEditText().setText("test@test.com");
        mobile.getEditText().setText("3001234567");
        phone.getEditText().setText("5000000");
        password.getEditText().setText("123457");
    }

    private void initUI(View view) {
        name = view.findViewById(R.id.field_name);
        name.getEditText().addTextChangedListener(this);
        surname = view.findViewById(R.id.field_surname);
        surname.getEditText().addTextChangedListener(this);
        customerId = view.findViewById(R.id.field_customer_id);
        customerId.getEditText().addTextChangedListener(this);
        email = view.findViewById(R.id.field_email);
        email.getEditText().addTextChangedListener(this);
        mobile = view.findViewById(R.id.field_mobile);
        mobile.getEditText().addTextChangedListener(this);
        phone = view.findViewById(R.id.field_phone);
        password = view.findViewById(R.id.field_password);
        password.getEditText().addTextChangedListener(this);
        btnSignUp = view.findViewById(R.id.btn_sign_up_now);
        btnSignUp.setEnabled(false);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up_now:
                SignUpRequest signUpRequest = new SignUpRequest();
                signUpRequest.setName(name.getEditText().getText().toString());
                signUpRequest.setSurname(surname.getEditText().getText().toString());
                signUpRequest.setCustomerId(customerId.getEditText().getText().toString());
                signUpRequest.setEmail(email.getEditText().getText().toString());
                signUpRequest.setMobile(mobile.getEditText().getText().toString());
                signUpRequest.setPhone(phone.getEditText().getText().toString());
                signUpRequest.setPassword(password.getEditText().getText().toString());

                try {
                    registerPresenter.callServiceSignUp(signUpRequest);
                } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException
                        | KeyManagementException | IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void sendToLogin() {
        Fragment fragment = new LoginFragment();

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, LoginFragment.FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(LoginFragment.FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    //<editor-fold desc="TextChangeListener">
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        btnSignUp.setEnabled(!isValidFields());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private boolean isValidFields() {
        return isEmpty(name.getEditText()) || isEmpty(surname.getEditText()) || isEmpty(customerId.getEditText())
                || isEmpty(email.getEditText()) || !isValidEmail(email.getEditText()) || isEmpty(mobile.getEditText()) || isEmpty(password.getEditText());
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean isValidEmail(EditText etText) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(etText.getText()).matches();
    }

    //</editor-fold>
}
