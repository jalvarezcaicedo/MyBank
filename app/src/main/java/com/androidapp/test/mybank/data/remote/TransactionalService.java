package com.androidapp.test.mybank.data.remote;

import com.androidapp.test.mybank.MyBankApplication;
import com.androidapp.test.mybank.R;
import com.androidapp.test.mybank.data.model.customerinfo.CustomerInformationResponse;
import com.androidapp.test.mybank.data.model.customerinfo.CustomerProductResponse;
import com.androidapp.test.mybank.data.model.login.Login;
import com.androidapp.test.mybank.data.model.signup.SignUpRequest;
import com.androidapp.test.mybank.data.model.signup.SignUpResponse;
import com.androidapp.test.mybank.data.model.transaction.TransactionRequest;
import com.androidapp.test.mybank.data.model.transaction.TransactionResponse;
import com.androidapp.test.mybank.util.Constants;
import com.androidapp.test.mybank.util.SingletonSharedPreference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class TransactionalService {

    public static RetrofitInterface newTransactionalService() throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();


        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add("localhost", "sha256/URRP0/Bw8F3eMJ5Sn6m/C+aYOEK97e82x+l0cjXVRzM=")
                .build();


        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca;
        try (InputStream caInput = new BufferedInputStream(MyBankApplication.getContext().getResources().openRawResource(R.raw.mybank_client))) {
            ca = cf.generateCertificate(caInput);
        }
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[]{trustManager}, null);
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .certificatePinner(certificatePinner)
                .hostnameVerifier((s, sslSession) -> {
                    HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                    return hv.verify("localhost", sslSession);
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new AddHeaderInterceptor())
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://" + SingletonSharedPreference.getInstance().getSharedPrefs().getString(Constants.SERVER, Constants.DEFAULT_SERVER) + ":4443/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(RetrofitInterface.class);

    }

    public interface RetrofitInterface {
        @POST("/login")
        Observable<Response<Void>> loginCall(@Body Login login);

        @POST("/api/1.0/customer/sign-up")
        Observable<Response<SignUpResponse>> signUpCall(@Body SignUpRequest signUpRequest);

        @GET
        Observable<Response<CustomerInformationResponse>> customerByIdCall(@Url String url, @Header("Authorization") String authorization);

        @GET
        Observable<Response<List<CustomerProductResponse>>> customerProductsCall(@Url String url);

        @GET
        Observable<Response<CustomerProductResponse>> customerProductInfoCall(@Url String url, @Header("Authorization") String authorization);

        @GET
        Observable<Response<List<TransactionResponse>>> lastTransactionsCall(@Url String url, @Header("Authorization") String authorization);

        @POST("/api/1.0/transaction/save")
        Observable<Response<TransactionResponse>> saveTransactionCall(@Header("Authorization") String authorization, @Body TransactionRequest transactionRequest);

    }

    public static class AddHeaderInterceptor implements Interceptor {

        AddHeaderInterceptor() {
        }

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Authorization", SingletonSharedPreference.getInstance().getSharedPrefs().getString(Constants.AUTHORIZATION_HEADER, ""));

            return chain.proceed(builder.build());
        }
    }
}
