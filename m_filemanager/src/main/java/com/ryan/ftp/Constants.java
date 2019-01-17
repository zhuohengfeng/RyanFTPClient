package com.ryan.ftp;

import android.os.Environment;

import java.io.File;

public class Constants {
    public final static String FTP_IP = "10.88.1.67";
    public final static int FTP_PORT = 1122;
    public final static String FTP_USERNAME = "rokid";
    public final static String FTP_PASSWORD = "rokid";

    // 对应FTP服务器上的目录
    public static final String FTP_SERVER_PATH = "";
    // 对应FTP服务器上的目录Camera
    public static final String FTP_PATH_CAMEAR = FTP_SERVER_PATH  + File.separator + "Camera" + File.separator;
    // 对应FTP服务器上的目录thubnial
    public static final String FTP_PATH_THUMBNAIL = FTP_SERVER_PATH  + File.separator + ".thumbnail" + File.separator;


    /**
     * 软件使用路径配置
     */
    public static final String APP_ROOT_PATH_RELATIVE = "com.example.ftpdemo";// 软件相对根路径
    public static final String APP_ROOT_SD_PATH_ABSOLUTE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator;// 软件绝对SD根路径

    public static final String FTP_PATH_RELATIVE = "ftp"; // ftp下载相对路径


    // FTP 状态
    public static final int FTP_CONNECT_SUCCESS = 0x0;// ftp连接成功
    public static final int FTP_CONNECT_FAIL = 0x1;// ftp连接失败
    public static final int FTP_DISCONNECT_SUCCESS = 0x2;// ftp断开连接
    public static final int FTP_FILE_NOTEXISTS = 0x3;// ftp上文件不存在

    public static final int FTP_UPLOAD_SUCCESS = 0x4;// ftp文件上传成功
    public static final int FTP_UPLOAD_FAIL = 0x5;// ftp文件上传失败
    public static final int FTP_UPLOAD_LOADING = 0x6;// ftp文件正在上传

    public static final int FTP_DOWN_LOADING = 0x7;// ftp文件正在下载
    public static final int FTP_DOWN_SUCCESS = 0x8;// ftp文件下载成功
    public static final int FTP_DOWN_FAIL = 0x9;// ftp文件下载失败

    public static final int FTP_DELETEFILE_SUCCESS = 0x10;// ftp文件删除成功
    public static final int FTP_DELETEFILE_FAIL = 0x11;// ftp文件删除失败

    public static final int FTP_LISTFILE_SUCCESS = 0x12;// ftp文件获取成功
    public static final int FTP_LISTFILE_FAIL = 0x13;// ftp文件获取失败

    public static final int MESSAGE_OFFSET = 0x123321;// Message 偏移量
    public static final int IMAGE_LOAD_SUCCESS = 0x123456;// 异步图片加载成功消息

}
