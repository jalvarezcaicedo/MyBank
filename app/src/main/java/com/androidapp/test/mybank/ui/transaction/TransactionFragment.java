package com.androidapp.test.mybank.ui.transaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidapp.test.mybank.R;
import com.androidapp.test.mybank.data.model.customerinfo.CustomerProductResponse;
import com.androidapp.test.mybank.data.model.transaction.TransactionResponse;
import com.androidapp.test.mybank.ui.base.BaseFragment;
import com.androidapp.test.mybank.ui.transaction.newtransaction.SaveTransactionFragment;
import com.androidapp.test.mybank.util.Constants;
import com.androidapp.test.mybank.util.Util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

public class TransactionFragment extends BaseFragment implements TransactionView, AdapterView.OnItemSelectedListener {

    public static final String FRAGMENT_TAG = "FRAGMENT_TRANSACTION";
    private SharedPreferences sharedPreferences;
    private TransactionPresenter transactionPresenter;
    private Spinner prodNumberField;
    private RecyclerView recyclerTransactions;
    private TextView emptyView;
    private TransactionAdapter transactionAdapter;
    private ArrayList<TransactionResponse> transactionResponses;
    private ArrayList<String> prodNumberList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        transactionPresenter = new TransactionPresenter();
        transactionPresenter.attachView(this);
        setHasOptionsMenu(true);
        transactionResponses = new ArrayList<>();
        initUI(view);

        return view;
    }

    private ArrayList<String> getProductNumberList(List<CustomerProductResponse> listProducts) {

        ArrayList<String> prodNumbStringList = new ArrayList<>();
        for (CustomerProductResponse customerProductResponse : listProducts) {
            prodNumbStringList.add(customerProductResponse.getProductNumber());
        }

        return prodNumbStringList;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tx, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save_tx:
                Fragment fragment = new SaveTransactionFragment();

                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, SaveTransactionFragment.FRAGMENT_TAG);
                fragmentTransaction.addToBackStack(SaveTransactionFragment.FRAGMENT_TAG);
                fragmentTransaction.commit();
                break;
            default:
                Log.i(getClass().getSimpleName(), "No se reconoce la opcion indicada");
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI(android.view.View view) {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        emptyView = view.findViewById(R.id.empty_transaction);
        prodNumberField = view.findViewById(R.id.transaction_query_product);
        prodNumberField.setOnItemSelectedListener(this);
        recyclerTransactions = view.findViewById(R.id.recycler_last_transactions);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerTransactions.setLayoutManager(llm);


        prodNumberList = getProductNumberList(Util.getListProducts(getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
                .getString(Constants.PRODUCTS, "")));

        if (prodNumberList.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, prodNumberList);
            prodNumberField.setAdapter(adapter);
        } else {
            prodNumberField.setEnabled(false);
        }

        transactionAdapter = new TransactionAdapter(getActivity(), transactionResponses);
        transactionAdapter.notifyDataSetChanged();
        recyclerTransactions.setAdapter(transactionAdapter);
        recyclerTransactions.setHasFixedSize(true);
    }

    @Override
    public void showTransactionList(List<TransactionResponse> listTransaction) {
        transactionResponses.clear();
        transactionAdapter.clearData();
        if (listTransaction != null && listTransaction.size() > 0) {
            transactionResponses.addAll(listTransaction);
        }
        transactionAdapter.notifyDataSetChanged();
        verifyVisibilityDataState();
    }

    private boolean verifyVisibilityDataState() {
        if (transactionResponses.size() > 0) {
            recyclerTransactions.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            return true;
        } else {
            recyclerTransactions.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            return false;
        }
    }

    //<editor-fold desc="TextWatcher">

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            if (prodNumberList.size() > 0) {
                transactionPresenter.callLastTransactions(sharedPreferences.getString(Constants.LIMIT, "1"), prodNumberList.get(i));
            }
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //</editor-fold>
}
