package com.zhongsou.fresco.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;

import com.zhongsou.fresco.MainApplication;
import com.zhongsou.fresco.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by yelong on 2015/11/10.
 */
public class FileUtil {
    public static final String FILE_SAVE = "mygirls";
    public static final String IMAGE_PIPELINE_CACHE_DIR = "girls";

    /**
     * 获取文件夹总大小
     */
    public static double getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children) {
                    size += getDirSize(f);
                }
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                return (double) file.length() / 1024 / 1024;
            }
        } else {
            return 0.0;
        }
    }

    public static String getSaveDirPath() {
        return Environment
                .getExternalStorageDirectory().getAbsolutePath() + File.separator + FILE_SAVE;
    }

    public static String getSavePicName(File cacheFile) {
        return "meinv" + cacheFile.getName().substring(0, 8) + ".jpg";
    }

    /**
     * 保存图片
     *
     * @param cacheFile
     */
    public static void savePicture(File cacheFile) {
        if (cacheFile == null || !cacheFile.exists()) {
            ShowToast.Short(ToastMsg.PIC_LOADING);
            return;
        }
        boolean isSmallPic = false;
        File picDir = new File(getSaveDirPath());

        if (!picDir.exists()) {
            picDir.mkdir();
        }

        final File newFile = new File(picDir, getSavePicName(cacheFile));

        if (newFile.exists()) {
            ShowToast.Short(ToastMsg.PIC_SAVEED);
            return;
        }
        if (FileUtil.copyTo(cacheFile, newFile)) {
            //保存成功之后，更新媒体库
            MediaScannerConnection.scanFile(MainApplication.getContext(), new String[]{newFile
                    .getAbsolutePath()}, null, new MyMediaScannerConnectionClient(isSmallPic,
                    newFile));
        } else {
            ShowToast.Short(ToastMsg.SAVE_FAILED);
        }

    }

    /**
     * 分享图片
     *
     * @param activity
     * @param cacheFile
     * @param shareText
     */
    public static void sharePicture(Activity activity, File cacheFile, String shareText) {
        if (cacheFile == null || !cacheFile.exists()) {
            ShowToast.Short(ToastMsg.PIC_LOADING);
            return;
        }

        File picDir = new File(getOwnCacheDirectory(MainApplication.getContext()), IMAGE_PIPELINE_CACHE_DIR);

        if (!picDir.exists()) {
            picDir.mkdir();
        }

        final File newFile = new File(picDir, getSavePicName(cacheFile));

        if (newFile.exists() || FileUtil.copyTo(cacheFile, newFile)) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            if (newFile != null && newFile.exists() && newFile.isFile()) {
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(newFile));
            } else {
                ShowToast.Short(ToastMsg.SHARE_PIC_NOEXIST);
                return;
            }

            intent.putExtra(Intent.EXTRA_TEXT, shareText);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(Intent.createChooser(intent, activity.getResources().getString(R
                    .string.app_name)));
        } else {
            ShowToast.Short(ToastMsg.SAVE_FAILED);
        }


    }


    /**
     * 用于保存图片后刷新图片媒体库
     */
    private static class MyMediaScannerConnectionClient implements MediaScannerConnection
            .MediaScannerConnectionClient {

        private boolean isSmallPic;
        private File newFile;

        public MyMediaScannerConnectionClient(boolean isSmallPic, File newFile) {
            this.isSmallPic = isSmallPic;
            this.newFile = newFile;
        }

        @Override
        public void onMediaScannerConnected() {

        }

        @Override
        public void onScanCompleted(String path, Uri uri) {
            Looper.prepare();
            if (isSmallPic) {
                ShowToast.Short(ToastMsg.SAVE_SMALL_SUCCESS + " \n相册" + File.separator +
                        FILE_SAVE + File.separator + newFile.getName());
            } else {
                ShowToast.Short(ToastMsg.SAVE_SUCCESS + " \n相册" + File.separator +
                        FILE_SAVE + File.separator + newFile.getName());
            }
            Looper.loop();
        }
    }

    /**
     * 复制文件
     *
     * @param src 源文件
     * @param dst 目标文件
     * @return
     */
    public static boolean copyTo(File src, File dst) {

        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(src);
            fo = new FileOutputStream(dst);
            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {

                if (fi != null) {
                    fi.close();
                }

                if (in != null) {
                    in.close();
                }

                if (fo != null) {
                    fo.close();
                }

                if (out != null) {
                    out.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

    }

    public static File getOwnCacheDirectory(Context context) {
        File appCacheDir = null;
        if ("mounted".equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = Environment.getExternalStorageDirectory();
        }

        if (appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = context.getCacheDir();
        }

        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }
}
