package com.ryan.ftp.presenter;

import com.ryan.ftp.view.IBaseView;

public class BasePresenter<V extends IBaseView> {
    public V mView;

    public BasePresenter(V view){
        mView = view;
    }
}