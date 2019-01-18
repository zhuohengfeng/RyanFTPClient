package com.ryan.ftp.presenter;

import android.util.Log;

import com.ryan.ftp.model.FileItemBean;
import com.ryan.ftp.view.IFileItemView;

import java.util.ArrayList;

public class FileItemPresenter extends BasePresenter<IFileItemView> {

    public FileItemPresenter(IFileItemView view) {
        super(view);
    }

    public void getFileItemData(ArrayList<FileItemBean> fileList, int page, int pageSize) {
        if (fileList != null) {
            try {
                int fromIndex = (page - 1) * pageSize;
                int toIndex = page * pageSize;
                ArrayList<FileItemBean> data = new ArrayList<>();
                Log.d("zhf1234", "getFileItemData fromIndex="+fromIndex+", toIndex="+toIndex);
                for (int i = fromIndex; i < toIndex; i++) {
                    data.add(fileList.get(i));
                }
                mView.onDownload(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            mView.onError();
        }

    }
}
