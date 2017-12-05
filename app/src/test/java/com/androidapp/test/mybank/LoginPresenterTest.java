package com.androidapp.test.mybank;

import com.androidapp.test.mybank.data.model.login.Login;
import com.androidapp.test.mybank.ui.login.LoginPresenter;
import com.androidapp.test.mybank.ui.login.LoginView;
import com.androidapp.test.mybank.util.Constants;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Created by jhonatanalvarezcaicedo on 27/11/17.
 */

public class LoginPresenterTest {

    @Mock
    LoginView loginView;
    private LoginPresenter presenter;

    @Before
    public void prepareDataTest() {
        MockitoAnnotations.initMocks(LoginPresenterTest.this);
        presenter = new LoginPresenter(null);
        presenter.attachView(loginView);
    }

    @Test
    public void hola() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        presenter.callServiceLogin(new Login());
        Mockito.verify(presenter.getMvpView(), Mockito.never()).showProgressDialog(Constants.STRING_PLEASE_WAIT);
    }
}
