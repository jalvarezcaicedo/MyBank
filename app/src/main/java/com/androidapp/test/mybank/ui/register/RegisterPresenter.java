package com.androidapp.test.mybank.ui.register;

import android.util.Log;

import com.androidapp.test.mybank.data.datamanager.DataManager;
import com.androidapp.test.mybank.data.model.signup.SignUpRequest;
import com.androidapp.test.mybank.ui.base.BasePresenter;
import com.androidapp.test.mybank.util.Constants;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter extends BasePresenter<RegisterView> {

    private CompositeDisposable disposables;
    private DataManager dataManager;

    RegisterPresenter() {
        this.dataManager = new DataManager();
    }

    @Override
    public void attachView(RegisterView mvpView) {
        super.attachView(mvpView);
        if (disposables == null)
            disposables = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        super.detachView();
        if (disposables != null)
            disposables.clear();
    }

    void callServiceSignUp(SignUpRequest signUpRequest) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.callRegister(signUpRequest).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(loginResponse -> {
                    getMvpView().hideProgressDialog();
                    getMvpView().sendToLogin();
                    getMvpView().createAlertDialog();
                    getMvpView().showAlertDialog("Status->" + loginResponse.body().getStatus().getStatusDesc() + "\n"
                            + "Response-> " + loginResponse.body().getResponse());
                }, throwable -> {
                    getMvpView().hideProgressDialog();
                    getMvpView().sendToLogin();
                    getMvpView().createAlertDialog();
                    getMvpView().showAlertDialog("Unknown error from app");
                    Log.i(getClass().getSimpleName(), "Transactional error");
                }));
    }
}
