package com.androidapp.test.mybank.ui.transaction;

import com.androidapp.test.mybank.data.model.transaction.TransactionResponse;
import com.androidapp.test.mybank.ui.base.BaseView;

import java.util.List;

public interface TransactionView extends BaseView {

    void showTransactionList(List<TransactionResponse> listTransaction);

}
