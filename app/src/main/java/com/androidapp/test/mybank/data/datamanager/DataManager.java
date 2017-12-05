package com.androidapp.test.mybank.data.datamanager;

import com.androidapp.test.mybank.data.model.customerinfo.CustomerInformationResponse;
import com.androidapp.test.mybank.data.model.customerinfo.CustomerProductResponse;
import com.androidapp.test.mybank.data.model.login.Login;
import com.androidapp.test.mybank.data.model.signup.SignUpRequest;
import com.androidapp.test.mybank.data.model.signup.SignUpResponse;
import com.androidapp.test.mybank.data.model.transaction.TransactionRequest;
import com.androidapp.test.mybank.data.model.transaction.TransactionResponse;
import com.androidapp.test.mybank.data.remote.TransactionalService;
import com.androidapp.test.mybank.util.Constants;
import com.androidapp.test.mybank.util.SingletonSharedPreference;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;

public class DataManager {

    public Observable<Response<Void>> callLogin(Login login) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        return TransactionalService.newTransactionalService().loginCall(login);
    }

    public Observable<Response<SignUpResponse>> callRegister(SignUpRequest signUpRequest) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        return TransactionalService.newTransactionalService().signUpCall(signUpRequest);
    }

    public Observable<Response<CustomerInformationResponse>> callCustomerById() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        String url = "/api/1.0/customer/" + SingletonSharedPreference.getInstance().getSharedPrefs().getString(Constants.CUSTOMER_ID, "") + "/get";
        String authorization = SingletonSharedPreference.getInstance().getSharedPrefs().getString(Constants.AUTHORIZATION_HEADER, "");

        return TransactionalService.newTransactionalService().customerByIdCall(url, authorization);
    }

    public Observable<Response<List<CustomerProductResponse>>> callCustomerProducts() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        String url = "/api/1.0/product/" + SingletonSharedPreference.getInstance().getSharedPrefs().getString(Constants.CUSTOMER_ID, "") + "/get";
        //String authorization = SingletonSharedPreference.getInstance().getSharedPrefs().getString(Constants.AUTHORIZATION_HEADER, "");

        return TransactionalService.newTransactionalService().customerProductsCall(url);
    }

    public Observable<Response<CustomerProductResponse>> callCustomerProductInfo(String productNumber) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        String url = "/api/1.0/product/" + SingletonSharedPreference.getInstance().getSharedPrefs().getString(Constants.CUSTOMER_ID, "") + "/" + productNumber + "/get";
        String authorization = SingletonSharedPreference.getInstance().getSharedPrefs().getString(Constants.AUTHORIZATION_HEADER, "");

        return TransactionalService.newTransactionalService().customerProductInfoCall(url, authorization);
    }

    public Observable<Response<List<TransactionResponse>>> callTransaction(String limit, String productNumber) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        String url = "/api/1.0/transaction/getLast/" + limit + "/" + SingletonSharedPreference.getInstance().getSharedPrefs().getString(Constants.CUSTOMER_ID, "") + "/" + productNumber;
        String authorization = SingletonSharedPreference.getInstance().getSharedPrefs().getString(Constants.AUTHORIZATION_HEADER, "");

        return TransactionalService.newTransactionalService().lastTransactionsCall(url, authorization);
    }

    public Observable<Response<TransactionResponse>> callSaveTransaction(TransactionRequest transactionRequest) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        String authorization = SingletonSharedPreference.getInstance().getSharedPrefs().getString(Constants.AUTHORIZATION_HEADER, "");

        return TransactionalService.newTransactionalService().saveTransactionCall(authorization, transactionRequest);
    }

}
