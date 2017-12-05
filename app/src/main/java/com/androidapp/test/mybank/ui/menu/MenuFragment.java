package com.androidapp.test.mybank.ui.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidapp.test.mybank.R;
import com.androidapp.test.mybank.ui.base.BaseFragment;
import com.androidapp.test.mybank.ui.product.CustomerProductFragment;
import com.androidapp.test.mybank.ui.transaction.TransactionFragment;

public class MenuFragment extends BaseFragment implements MenuView, View.OnClickListener {

    public static final String FRAGMENT_TAG = "FRAGMENT_MENU";


    //TODO si hay tiempo mostrar la data del usuario en la parte superior de la interfaz

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        initUI(view);
        MenuPresenter menuPresenter = new MenuPresenter();
        menuPresenter.attachView(MenuFragment.this);

        return view;
    }

    private void initUI(View view) {
        CardView getProduct = view.findViewById(R.id.option_product);
        getProduct.setOnClickListener(this);
        CardView saveTransaction = view.findViewById(R.id.option_transaction);
        saveTransaction.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.option_product:
                getInterfaceProduct();
                break;
            case R.id.option_transaction:
                getInterfaceTransaction();
        }
    }

    @Override
    public void getInterfaceProduct() {
        Fragment fragment = new CustomerProductFragment();

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, CustomerProductFragment.FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(CustomerProductFragment.FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void getInterfaceTransaction() {
        Fragment fragment = new TransactionFragment();

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, TransactionFragment.FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(TransactionFragment.FRAGMENT_TAG);
        fragmentTransaction.commit();
    }
}
