package com.androidapp.test.mybank.ui.transaction;

import android.content.Context;
import android.util.Log;

import com.androidapp.test.mybank.data.datamanager.DataManager;
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

public class TransactionPresenter extends BasePresenter<TransactionView> {

    private CompositeDisposable disposables;
    private DataManager dataManager;

    TransactionPresenter() {
        this.dataManager = new DataManager();
    }

    @Override
    public void attachView(TransactionView mvpView) {
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

    void callLastTransactions(String limit, String productNumber) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.callTransaction(limit, productNumber).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(lastTransactions -> {
                    Log.i(getClass().getSimpleName(), "# Tx's ->" + lastTransactions.body().size());
                    getMvpView().hideProgressDialog();
                    getMvpView().showTransactionList(lastTransactions.body());
                }, throwable -> {
                    getMvpView().hideProgressDialog();
                    getMvpView().showTransactionList(null);
                }));
    }
}
