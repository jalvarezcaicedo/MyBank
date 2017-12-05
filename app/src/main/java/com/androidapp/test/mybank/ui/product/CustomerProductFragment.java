package com.androidapp.test.mybank.ui.product;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidapp.test.mybank.R;
import com.androidapp.test.mybank.data.model.customerinfo.CustomerProductResponse;
import com.androidapp.test.mybank.ui.base.BaseFragment;
import com.androidapp.test.mybank.util.Constants;
import com.androidapp.test.mybank.util.Util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

public class CustomerProductFragment extends BaseFragment implements CustomerProductView, AdapterView.OnItemSelectedListener {

    public static final String FRAGMENT_TAG = "FRAGMENT_CUSTOMER_PRODUCT";
    private CustomerProductPresenter customerProductPresenter;
    private TextView productData;
    private Spinner productNumberSpinner;
    private ArrayList<String> prodNumberList;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_customer_product, container, false);
        initUI(view);
        customerProductPresenter = new CustomerProductPresenter();
        customerProductPresenter.attachView(this);

        return view;
    }

    private void initUI(android.view.View view) {
        productData = view.findViewById(R.id.customer_product_data);
        productNumberSpinner = view.findViewById(R.id.spinner_product_number);
        productNumberSpinner.setOnItemSelectedListener(this);

        prodNumberList = getProductNumberList(Util.getListProducts(getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
                .getString(Constants.PRODUCTS, "")));

        if (prodNumberList.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, prodNumberList);
            productNumberSpinner.setAdapter(adapter);
        } else {
            productNumberSpinner.setEnabled(false);
        }

    }

    private ArrayList<String> getProductNumberList(List<CustomerProductResponse> listProducts) {

        ArrayList<String> prodNumbStringList = new ArrayList<>();
        for (CustomerProductResponse customerProductResponse : listProducts) {
            prodNumbStringList.add(customerProductResponse.getProductNumber());
        }

        return prodNumbStringList;
    }

    @Override
    public void showCustomerProductData(CustomerProductResponse body) {
        if (body != null)
            productData.setText(body.toString());
        else
            productData.setText(getString(R.string.invalid_product_data));
    }

    //<editor-fold desc="ItemSelectedListeners">

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            if (prodNumberList.size() > 0)
                customerProductPresenter.callCustomerProduct(prodNumberList.get(i));
            else
                productData.setText(getString(R.string.no_product_data));
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //</editor-fold>
}
