package com.ryan.ftp.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.ryan.ftp.BaseApplication;
import com.ryan.ftp.Constants;
import com.ryan.ftp.ftp.FTPUtils;
import com.ryan.ftp.model.FileItemBean;
import com.ryan.ftp.utils.FileUtil;
import com.ryan.ftp.utils.JSONHelper;
import com.ryan.ftp.view.IFileItemView;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


public class FileListPresenter extends BasePresenter<IFileItemView> {
    private static final String TAG = "FileListPresenter";
    
    public FileListPresenter(IFileItemView view) {
        super(view);
    }

    public void getFileListFromFtp() {
        new GetFileListTask().execute();
    }

    class GetFileListTask extends AsyncTask<String, Void, ArrayList<FileItemBean>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<FileItemBean> doInBackground(String... params) {
            ArrayList<FileItemBean> allFileMapList = new ArrayList<>();

            try {
                // 下载地址
                String downloadPath = BaseApplication.getContext().getCacheDir() + File.separator;
                if (FileUtil.isFileExist(downloadPath + Constants.JSON_FILE_NAME)) {
                    FileUtil.deleteFile(downloadPath + Constants.JSON_FILE_NAME);
                }

                FTPUtils.getInstance().openConnect();
                FTPUtils.getInstance().downloadSingleFile(
                        Constants.FTP_PATH_FILE_MAP, // 服务器名字
                        downloadPath, Constants.JSON_FILE_NAME, null);

                File jsonFile = new File(downloadPath + Constants.JSON_FILE_NAME);
                if (jsonFile!=null && jsonFile.exists()) {
                    Log.d("zhf123", "下载成功 jsonFile="+jsonFile.getAbsolutePath());

                    String input = FileUtils.readFileToString(jsonFile, "UTF-8");
                    JSONObject jsonObject = JSONHelper.getJSONObject(input);

                    int i = 0;
                    for(Iterator<String> iterator = jsonObject.keys(); iterator.hasNext();){
                        String key = iterator.next();
                        String value = jsonObject.getString(key);
                        Log.d("zhf123", "jsonArray="+jsonObject);

                        FileItemBean fileItem = new FileItemBean();
                        fileItem.setTag(i++)
                        .setPath(key)
                        .setTpath(value);

                        allFileMapList.add(fileItem);
                    }

                }
                else {
                    Log.d("zhf123", "下载失败");
                }
            } catch (Exception e) {
                allFileMapList = null;
                e.printStackTrace();
            }
            return allFileMapList;
        }

        @Override
        protected void onPostExecute(ArrayList<FileItemBean> list) {
            super.onPostExecute(list);
            if (list != null) {
                mView.onCompleted(list);
            }
        }
    }



}
