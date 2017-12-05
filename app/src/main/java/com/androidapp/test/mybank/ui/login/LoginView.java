package com.androidapp.test.mybank.ui.login;


import com.androidapp.test.mybank.ui.base.BaseView;

public interface LoginView extends BaseView {

    void sendToMenu();

    void showSnackBar(String msg);

    void getCustomerInfo();
}
