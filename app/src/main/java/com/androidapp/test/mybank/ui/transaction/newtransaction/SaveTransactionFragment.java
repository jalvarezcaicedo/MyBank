package com.androidapp.test.mybank.ui.transaction.newtransaction;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.androidapp.test.mybank.R;
import com.androidapp.test.mybank.data.model.transaction.TransactionRequest;
import com.androidapp.test.mybank.data.model.transaction.TransactionResponse;
import com.androidapp.test.mybank.ui.base.BaseFragment;
import com.androidapp.test.mybank.ui.transaction.TransactionFragment;
import com.androidapp.test.mybank.util.Constants;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class SaveTransactionFragment extends BaseFragment implements SaveTransactionView {

    public static final String FRAGMENT_TAG = "FRAGMENT_SAVE_TRANSACTION";
    private SaveTransactionPresenter saveTransactionPresenter;
    private RelativeLayout containerNewTx;
    private EditText productNumber;
    private EditText channelId;
    private EditText amount;
    private EditText type;
    private Button btnSaveTx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_transaction, container, false);
        initUI(view);
        saveTransactionPresenter = new SaveTransactionPresenter();
        saveTransactionPresenter.attachView(this);

        return view;
    }

    private void initUI(View view) {
        containerNewTx = view.findViewById(R.id.container_new_transaction);
        productNumber = view.findViewById(R.id.new_product_number);
        channelId = view.findViewById(R.id.new_channel_id);
        amount = view.findViewById(R.id.new_amount);
        type = view.findViewById(R.id.new_type);
        btnSaveTx = view.findViewById(R.id.btn_save_transaction);

        btnSaveTx.setOnClickListener(viewOnClickListener -> {
            if (!TextUtils.isEmpty(productNumber.getText()) || !TextUtils.isEmpty(channelId.getText()) ||
                    !TextUtils.isEmpty(amount.getText()) || !TextUtils.isEmpty(type.getText())) {

                TransactionRequest transactionRequest = new TransactionRequest();
                transactionRequest.setCustomerId(getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.CUSTOMER_ID, ""));
                transactionRequest.setProductNumber(productNumber.getText().toString());
                transactionRequest.setChannelId(Integer.parseInt(channelId.getText().toString()));
                transactionRequest.setAmount(Double.parseDouble(amount.getText().toString()));
                transactionRequest.setType(Integer.parseInt(type.getText().toString()));

                try {
                    saveTransactionPresenter.callSaveTransaction(transactionRequest);
                } catch (CertificateException | NoSuchAlgorithmException | IOException | KeyStoreException | KeyManagementException e) {
                    e.printStackTrace();
                }
            } else {
                Snackbar.make(containerNewTx, getString(R.string.validate_fields_tx), Snackbar.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void saveTransaction(TransactionResponse transactionResponse) {
        String message = "Successful response \n"
                + "Tx #-> " + transactionResponse.getTransactionNumber() + "\n"
                + "Date-> " + transactionResponse.getDate() + "\n"
                + "Status-> " + transactionResponse.getStatus();
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        showSnackBarResponse("200", message, true);
        getActivity().onBackPressed();
    }

    @Override
    public void showSnackBarResponse(String code, String response, boolean successful) {
        Snackbar snackbar = Snackbar.make(containerNewTx, "CODE " + code + ":\n" + response, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.accept), view -> {
            if (successful)
                sendToTransactionUI();
            else
                snackbar.dismiss();
        });
    }

    @Override
    public void sendToTransactionUI() {
        Fragment fragment = new TransactionFragment();

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, TransactionFragment.FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(TransactionFragment.FRAGMENT_TAG);
        fragmentTransaction.commit();
    }
}
