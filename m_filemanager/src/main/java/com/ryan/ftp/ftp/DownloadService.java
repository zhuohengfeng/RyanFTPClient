package com.ryan.ftp.ftp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.util.Log;

import com.ryan.ftp.BaseApplication;
import com.ryan.ftp.Constants;
import com.ryan.ftp.model.FileItemBean;
import com.ryan.ftp.utils.CommonUtils;
import com.ryan.ftp.utils.FileUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DownloadService extends IntentService {
    public DownloadService() {
        super("DownloadService");
    }

    // 创建线程池对内存进行优化处理
    private ExecutorService executorService;

    public static void startService(Context context, ArrayList<FileItemBean> datas/*, String subtype*/) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) datas);
        //intent.putExtra("subtype", subtype);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 最大五条线程同时执行
        executorService = Executors.newFixedThreadPool(10);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        ArrayList<FileItemBean> datas = intent.getParcelableArrayListExtra("data");
        //String subtype = intent.getStringExtra("subtype");
        handleGirlItemData(datas /*, subtype*/);
    }

    private void handleGirlItemData(ArrayList<FileItemBean> datas /*, String subtype*/) {
        Log.d("zhf123", "handleGirlItemData datas.size()="+datas.size());
        if (datas.size() == 0) {
            EventBus.getDefault().post("finish");
            return;
        }
        int i = 0;
        for (FileItemBean data : datas) {
            String localPath = downloadFileFromFtpServer(Constants.FTP_SERVER_PATH, data, i++);
            if (FileUtil.isFileExist(localPath)) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeFile(localPath, options);
                int width = options.outWidth;
                int height = options.outHeight;
                //Log.i("zhf1234", "下载完成=>localPath="+localPath+", i="+i+", width:" + width + ", height:" + height);

                data.setPath(localPath)
                        .setTwidth(width)
                        .setTheight(height);
                Log.i("zhf1234", "下载完成=>data="+data);
            }


            // 这里开始下载图片
//            Bitmap bitmap = ImageLoader.load(this, data.getUrl());
//            if (bitmap != null) {
//                data.setWidth(bitmap.getWidth());
//                data.setHeight(bitmap.getHeight());
//            }
//
//            data.setSubtype(subtype);
        }
        // 都下载完成后，调用这个来通知一次性更新UI
        EventBus.getDefault().post(datas);
    }

    // 开启多线程下载
    private String downloadFileFromFtpServer(String serverPath, final FileItemBean fileItem, final int indexInList) {
        final String serverPathWithFileName =  fileItem.getTpath();
        final String loaclPath = CommonUtils.getAbsoluteEnvironmentRootPath(BaseApplication.getContext()) // 本地缓存目录
                + Constants.FTP_PATH_RELATIVE + File.separator+ FileUtil.getFileName(fileItem.getTpath()) ;
        try {
            // 单文件下载
            FTPUtils.getInstance().downloadSingleFile(
                    serverPathWithFileName, // 服务器名字
                    CommonUtils.getAbsoluteEnvironmentRootPath(BaseApplication.getContext()) // 本地缓存目录
                    + Constants.FTP_PATH_RELATIVE + File.separator, FileUtil.getFileName(fileItem.getTpath()),

                    new FTPUtils.FtpProgressListener() {

                @Override
                public void onFtpProgress(int currentStatus, long process, File targetFile) {
                    Log.d("zhf123", "下载结果 index="+indexInList+", currentStatus="+currentStatus+", targetFile="+targetFile);
                    switch (currentStatus) {
                        case Constants.FTP_DOWN_SUCCESS:


//                                    if (CommonUtils.verifyFile(CommonUtils.getAbsoluteEnvironmentRootPath(BaseApplication.getContext()) + Config.FTP_PATH_RELATIVE
//                                            + File.separator + itemFtpObjList.get(indexInList).getFtpFile().getName(), itemFtpObjList
//                                            .get(indexInList).getFtpFile().getSize())) {
//                                        itemFtpObjList.get(indexInList).setDownLoadStatus(DownLoadStatus.FINISH);
//                                        itemFtpObjList.get(indexInList).setProgress(100);
//                                        handler.sendEmptyMessage(Constant.FTP_DOWN_SUCCESS + Constant.MESSAGE_OFFSET);
//                                    } else {
//                                        FileUtil.deleteFile(CommonUtil.getAbsoluteEnvironmentRootPath(context) + Config.FTP_PATH_RELATIVE
//                                                + File.separator + itemFtpObjList.get(indexInList).getFtpFile().getName());
//                                        // 重新下载
//                                        downloadFileFromFtpServer(Config.FTP_PATH_AA, itemFtpObjList.get(indexInList), indexInList);
//                                    }
                            break;
                        case Constants.FTP_DOWN_FAIL:
                            //handler.sendEmptyMessage(Constant.FTP_DOWN_FAIL + Constant.MESSAGE_OFFSET);
                            break;
                        case Constants.FTP_DOWN_LOADING:
//                                    itemFtpObjList.get(indexInList).setDownLoadStatus(DownLoadStatus.DOWNLOADING);
//                                    itemFtpObjList.get(indexInList).setProgress((int) process);
//                                    handler.sendEmptyMessage(Constant.FTP_DOWN_LOADING + Constant.MESSAGE_OFFSET);
                            break;
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return loaclPath;

    }



}