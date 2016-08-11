package com.zhongsou.fresco.utils;

import android.content.Context;

import com.zhongsou.fresco.view.CustomProgressDialog;

/**
 * Created by yelong on 2015/11/11.
 */
public class ProgressDialogUtil {
    private static CustomProgressDialog mDialog;

    public static void showProgressDialog(Context context, String msg) {
        mDialog = CustomProgressDialog.createProgressDialog(context, msg);
        if (null != mDialog) {
            mDialog.show();
            mDialog.setCancelable(true);
        }
    }

    public static void hideProgressDialog() {
        if (null != mDialog) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
