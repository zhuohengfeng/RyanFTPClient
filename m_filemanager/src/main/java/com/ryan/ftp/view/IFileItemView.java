package com.ryan.ftp.view;


import com.ryan.ftp.model.FileItemBean;

import java.util.ArrayList;


public interface IFileItemView extends IBaseView {
    void onCompleted(ArrayList<FileItemBean> data);

    void onDownload(ArrayList<FileItemBean> data);
}
