package com.androidapp.test.mybank.ui.login;

import android.content.SharedPreferences;
import android.util.Log;

import com.androidapp.test.mybank.MyBankApplication;
import com.androidapp.test.mybank.data.datamanager.DataManager;
import com.androidapp.test.mybank.data.model.customerinfo.CustomerProductResponse;
import com.androidapp.test.mybank.data.model.login.Login;
import com.androidapp.test.mybank.ui.base.BasePresenter;
import com.androidapp.test.mybank.util.Constants;
import com.androidapp.test.mybank.util.SingletonSharedPreference;
import com.androidapp.test.mybank.util.Util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginView> {

    private CompositeDisposable disposables;
    private DataManager dataManager;

    public LoginPresenter() {
        this.dataManager = new DataManager();
    }


    @Override
    public void attachView(LoginView mvpView) {
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

    public void callServiceLogin(Login login) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.callLogin(login).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(loginResponse -> {
                    getMvpView().hideProgressDialog();
                    if (loginResponse.code() == 200) {
                        SingletonSharedPreference.getInstance().getEditorPrefs().putString(Constants.CUSTOMER_ID, login.getCustomerId()).apply();
                        SingletonSharedPreference.getInstance().getEditorPrefs().putString(Constants.AUTHORIZATION_HEADER, loginResponse.headers().get(Constants.AUTHORIZATION_HEADER)).apply();

                        Log.i(getClass().getSimpleName(), "HEADER AUTH->" + SingletonSharedPreference.getInstance().getSharedPrefs().getString(Constants.AUTHORIZATION_HEADER, ""));
                        getMvpView().getCustomerInfo();
                    } else if (loginResponse.code() == 401) {
                        getMvpView().showSnackBar("Bad credentials: user or password wrong");
                    } else if (loginResponse.code() == 500) {
                        getMvpView().showSnackBar("Internal error server");
                    } else {
                        getMvpView().showSnackBar("Unknown error from server");
                    }
                }, throwable -> {
                    getMvpView().hideProgressDialog();
                    throwable.printStackTrace();
                    getMvpView().showSnackBar("Unknown error from app");
                }));
    }

    void callCustomerInfo() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.callCustomerById().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(signUpResponse -> {
                    getMvpView().hideProgressDialog();
                    SharedPreferences.Editor sharedPreferencesEditor = SingletonSharedPreference.getInstance().getEditorPrefs();
                    sharedPreferencesEditor.putString(Constants.NAME, signUpResponse.body().getName());
                    sharedPreferencesEditor.putString(Constants.CUSTOMER_ID, signUpResponse.body().getCustomerId());
                    sharedPreferencesEditor.putString(Constants.SURNAME, signUpResponse.body().getSurname());
                    sharedPreferencesEditor.putString(Constants.EMAIL, signUpResponse.body().getEmail());
                    sharedPreferencesEditor.putString(Constants.MOBILE, signUpResponse.body().getMobile());
                    sharedPreferencesEditor.putString(Constants.LIMIT, "5");
                    sharedPreferencesEditor.apply();

                    callCustomerProducts();
                }, throwable -> getMvpView().hideProgressDialog()));
    }

    void callCustomerProducts() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.callCustomerProducts().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(customerProductsResponse -> {
                    getMvpView().hideProgressDialog();
                    Util.setList(MyBankApplication.getContext(), Constants.PRODUCTS, customerProductsResponse.body());
                    getMvpView().sendToMenu();
                }, throwable -> {
                    getMvpView().hideProgressDialog();
                    Util.setList(MyBankApplication.getContext(), Constants.PRODUCTS, new ArrayList<CustomerProductResponse>());
                    getMvpView().sendToMenu();
                }));
    }

}
