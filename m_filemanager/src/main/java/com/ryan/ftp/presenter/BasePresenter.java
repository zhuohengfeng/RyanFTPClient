package com.ryan.ftp.presenter;

import com.ryan.ftp.view.IBaseView;

import rx.Subscription;

public class BasePresenter<V extends IBaseView> {
    public V mView;
    protected Subscription mSubscription;

    public BasePresenter(V view){
        mView = view;
    }

    public void detach() {
        mView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}