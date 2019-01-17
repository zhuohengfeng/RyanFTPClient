package com.ryan.ftp.presenter;

import android.util.Log;

import com.ryan.ftp.Constants;
import com.ryan.ftp.ftp.FTPUtils;
import com.ryan.ftp.model.FileItemBean;
import com.ryan.ftp.utils.RxManager;
import com.ryan.ftp.view.IFileItemView;

import org.apache.commons.net.ftp.FTPFileFilters;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

public class FilesInfoPresenter extends BasePresenter<IFileItemView> {

    private static final String TAG = "FilesInfoPresenter";
    
    public FilesInfoPresenter(IFileItemView view) {
        super(view);
    }

    public void getFilesInfo() {
        Observable observable = Observable.create(new Observable.OnSubscribe<ArrayList<FileItemBean>>() {
            @Override
            public void call(Subscriber<? super ArrayList<FileItemBean>> subscriber) {
                try {
                    FTPUtils.getInstance().openConnect();
                    ArrayList<FileItemBean> list = FTPUtils.getInstance().listsFiles(Constants.FTP_PATH_CAMEAR, FTPFileFilters.NON_NULL);

                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Subscriber<ArrayList<FileItemBean>> subscriber = new Subscriber<ArrayList<FileItemBean>>() {
            @Override
            public void onNext(ArrayList<FileItemBean> list) {
                Log.d(TAG, "Item: list=" + list.size());
                mView.onCompleted(list);
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Error!");
            }
        };

        RxManager.getInstance().doSubscribe(observable, subscriber );
    }
}
