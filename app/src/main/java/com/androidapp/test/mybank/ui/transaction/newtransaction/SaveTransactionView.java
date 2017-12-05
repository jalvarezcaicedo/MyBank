package com.androidapp.test.mybank.ui.transaction.newtransaction;

import com.androidapp.test.mybank.data.model.transaction.TransactionResponse;
import com.androidapp.test.mybank.ui.base.BaseView;

public interface SaveTransactionView extends BaseView {

    void saveTransaction(TransactionResponse transactionResponse);

    void showSnackBarResponse(String code, String response, boolean successful);

    void sendToTransactionUI();

}
