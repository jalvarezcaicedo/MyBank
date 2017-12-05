package com.androidapp.test.mybank.ui.product;

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

public class CustomerProductPresenter extends BasePresenter<CustomerProductView> {

    private CompositeDisposable disposables;
    private DataManager dataManager;

    CustomerProductPresenter() {
        this.dataManager = new DataManager();
    }

    @Override
    public void attachView(CustomerProductView mvpView) {
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

    void callCustomerProduct(String productNumber) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.callCustomerProductInfo(productNumber).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(customerProductResponse -> {
                    getMvpView().hideProgressDialog();
                    getMvpView().showCustomerProductData(customerProductResponse.body());
                }, throwable -> {
                    getMvpView().hideProgressDialog();
                    getMvpView().showCustomerProductData(null);
                }));
    }
}
