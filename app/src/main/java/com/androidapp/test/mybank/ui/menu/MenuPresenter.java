package com.androidapp.test.mybank.ui.menu;

import com.androidapp.test.mybank.ui.base.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;

public class MenuPresenter extends BasePresenter<MenuView> {

    private CompositeDisposable disposables;

    @Override
    public void attachView(MenuView mvpView) {
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
}
