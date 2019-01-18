package com.ryan.ftp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.ryan.ftp.Constants;

import org.jsoup.helper.StringUtil;

import java.io.File;
import java.lang.ref.WeakReference;

public class CommonUtils {
    /**
     * @Description TODO 通过绝对路径和指定宽高获取对应的bitmap
     * @param path
     *            绝对路径
     * @param w
     *            宽
     * @param h
     *            高
     * @return 对应bitmap
     */
    public static Bitmap getBitmapFromPath(String path, int w, int h) {
        if (StringUtil.isBlank(path) || w <= 0 || h <= 0) {
            return null;
        }
        if (!FileUtil.isFileExist(path)) {
            return null;
        }
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0f, scaleHeight = 0f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    /**
     * @Description TODO 获取软件根目录绝对路径
     * @return
     */
    public static String getAbsoluteEnvironmentRootPath(Context context) {
        String path = null;
        if (SDCardUtils.isSDCardEnable()) {
            path = Constants.APP_ROOT_SD_PATH_ABSOLUTE;
        } else {
            path = context.getApplicationContext().getFilesDir().getAbsolutePath() + File.separator;
        }
        return path;
    }


    /**
     * @Description TODO Bitmap 转化为Drawable
     * @param bitmap
     *            源Bitmap
     * @param context
     *            上下文
     * @return 返回对应的Drawable
     */
    public static Drawable bitmap2Drawable(Bitmap bitmap, Context context) {
        Resources res = context.getResources();
        Drawable drawable = new BitmapDrawable(res, bitmap);
        return drawable;
    }

    /**
     * @Description TODO 根据服务器文件长度校验数据是否完整
     * @param localPath
     *            本地文件存储路径（带后缀）
     * @param fileSize
     *            文件需要大小
     * @return
     */
    public static boolean verifyFile(String localPath, long fileSize) {
        File file = new File(localPath);
        if (file.exists() && file.isFile()) {
            if (fileSize == file.length()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     * @Description TODO 校验文件后缀名称是否为图片，目前只支持.jpg和.png
     * @param pathWithSuffix
     *            带有后缀的图片路径，绝对路径和相对路径均可
     * @return 布尔型
     */
    public static boolean verifyPicture(String pathWithSuffix) {
        if (StringUtil.isBlank(pathWithSuffix)) {
            return false;
        } else {
            String picNameWithSuffix = FileUtil.getFileName(pathWithSuffix);
            if (picNameWithSuffix.contains(".jpg") || picNameWithSuffix.contains(".png")) {
                return true;
            }
        }
        return false;
    }

}

